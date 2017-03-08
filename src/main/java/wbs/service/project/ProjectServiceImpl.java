package wbs.service.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wbs.model.wbs.elements.WBSElement;
import wbs.repository.ProjectRepository;
import wbs.model.project.Project;
import wbs.exceptions.ObjectNotFoundException;
import wbs.exceptions.UnauthorizedException;
import wbs.service.wbs.WBSNodeService;
import wbs.service.wbs.WBSTreeService;
import wbs.service.wbs.elements.WBSElementService;

import java.util.List;


@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private WBSElementService wbsElementService;

    @Autowired
    private WBSNodeService wbsNodeService;

    @Autowired
    private WBSTreeService wbsTreeService;

    @Override
    public List<Project> findAllForCurrentUser() {
        return projectRepository.findAllForCurrentUser();
    }

    @Override
    public Project findbyId(Long id) {
        if (projectRepository.findOne(id) == null) {
            throw new ObjectNotFoundException("The project was not found");
        }
        if (!currentUserIsAuthorized(id)) {
            throw new UnauthorizedException("You are not allowed to access this project");
        }
        return projectRepository.findForCurrentUser(id);
    }

    @Override
    public Project create(Project project) {
        Project savedProject = projectRepository.save(project);

        WBSElement element = savedProject.getWbsTree().getRoot().getElement();
        element.setName(savedProject.getName());
        wbsElementService.edit(element);

        return project;
    }

    @Override
    public Project edit(Project project) {
        if(currentUserIsAuthorized(project.getId())) {
            Project savedProject = projectRepository.save(project);

            WBSElement element = savedProject.getWbsTree().getRoot().getElement();
            element.setName(savedProject.getName());
            wbsElementService.edit(element);

            return savedProject;
        } else {
            throw new UnauthorizedException("You are not allowed to access this project");
        }
    }

    @Override
    public void deleteById(Long id) {
        if (projectRepository.findOne(id) == null) {
            throw new ObjectNotFoundException("The project was not found");
        }
        if(!currentUserIsAuthorized(id)) {
            throw new UnauthorizedException("You are not allowed to access this project");
        }
        projectRepository.delete(id);
    }

    @Override
    public boolean currentUserIsAuthorized(Long id) {
        if(projectRepository.findForCurrentUser(id) == null && projectRepository.findOne(id) != null) {
            return false;
        }
        return true;
    }


}
