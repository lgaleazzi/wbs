package wbs.service.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import wbs.model.wbs.elements.WBSElement;
import wbs.repository.ProjectRepository;
import wbs.model.project.Project;
import wbs.service.UnauthorizedException;
import wbs.service.wbs.WBSNodeService;
import wbs.service.wbs.WBSTreeService;
import wbs.service.wbs.elements.WBSElementService;

import java.nio.file.AccessDeniedException;
import java.util.List;

/**
 * Created by livia on 06.01.17.
 */
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
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public Project findbyId(Long id) {
        Project project = projectRepository.findForCurrentUser(id);
        if (project == null && projectRepository.findOne(id) != null) {
            throw new UnauthorizedException("You are not allowed to see this project");
        }
        return project;
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

        Project savedProject = projectRepository.save(project);

        WBSElement element = savedProject.getWbsTree().getRoot().getElement();
        element.setName(savedProject.getName());
        wbsElementService.edit(element);

        return savedProject;
    }

    @Override
    public void deleteById(Long id) {
        projectRepository.delete(id);
    }
}
