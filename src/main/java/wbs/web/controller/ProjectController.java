package wbs.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wbs.model.authentication.User;
import wbs.model.project.Project;
import wbs.service.authentication.UserService;
import wbs.service.project.ProjectService;
import wbs.web.FlashMessage;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@SessionAttributes({"project"})
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    //view all projects for current user
    @RequestMapping("/")
    public String listProjects(Model model) {
        List<Project> allProjects = projectService.findAllForCurrentUser();
        model.addAttribute("projects", allProjects);
        return "project/all";
    }

    //view details of one project
    @RequestMapping("/projects/{projId}")
    public String projectDetails(@PathVariable Long projId, Model model) {
        Project project = projectService.findbyId(projId);
        model.addAttribute("project", project);
        return "project/details";
    }

    //form to edit a project
    @RequestMapping("/projects/{projId}/edit")
    public String formEditProject(@PathVariable Long projId, Model model) {
        Project project = projectService.findbyId(projId);

        //repopulate form data in case of errors, otherwise populate current project data
        if (model.containsAttribute("repopulateProject")) {
            model.addAttribute("project", model.asMap().get("repopulateProject"));
        } else {
            model.addAttribute("project", project);
        }

        model.addAttribute("action", String.format("/projects/%s", projId));
        model.addAttribute("heading", String.format("Edit project '%s'", project.getName()));
        return "/project/form";
    }

    //form to add a project
    @RequestMapping("/projects/add")
    public String formAddProject(Model model) {

        //repopulate form data in case of errors, otherwise add new project
        if (model.containsAttribute("repopulateProject")) {
            model.addAttribute("project", model.asMap().get("repopulateProject"));
        } else {
            model.addAttribute("project", new Project());
        }
        model.addAttribute("action", "/");
        model.addAttribute("heading", "Create new project");
        return "/project/form";
    }

    //Update an existing project
    @RequestMapping(value = "/projects/{projId}", method = RequestMethod.POST)
    public String updateProject(@ModelAttribute("project") @Valid Project project, BindingResult result, RedirectAttributes redirectAttributes) {

        //if errors exist, add errors to model on redirect, and add project back to repopulate form data
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.project", result);
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("Please check form data", FlashMessage.Status.DANGER));
            redirectAttributes.addFlashAttribute("repopulateProject", project);
            return String.format("redirect:/projects/%s/edit", project.getId());
        }

        projectService.edit(project);
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Project updated", FlashMessage.Status.SUCCESS));
        return String.format("redirect:/projects/%s", project.getId());
    }

    //Add a project and redirect to projects page
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String addProject(@Valid Project project, BindingResult result, RedirectAttributes redirectAttributes, Principal principal) {

        //if errors exist, add errors to model on redirect, and add project back to repopulate form data
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.project", result);
            redirectAttributes.addFlashAttribute("repopulateProject", project);
            return String.format("redirect:/projects/add");
        }

        //if no errors, associate project data with username and create project
        User user = userService.findByUsername(principal.getName());
        project.setUser(user);
        projectService.create(project);
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Project created", FlashMessage.Status.SUCCESS));

        return "redirect:/";
    }

    //Delete a project and redirect to projects page
    @RequestMapping(value = "/projects/{projId}/delete", method = RequestMethod.POST)
    public String deleteProject(@PathVariable Long projId, Project project, RedirectAttributes redirectAttributes) {
        projectService.deleteById(projId);
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Project deleted", FlashMessage.Status.SUCCESS));
        return "redirect:/";
    }

}
