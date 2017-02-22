package wbs.service.project;

import wbs.model.project.Project;

import java.util.List;

/**
 *
 */
public interface ProjectService {

    List<Project> findAll();
    Project findbyId(Long id);
    Project create(Project project);
    Project edit(Project project);
    void deleteById(Long id);

}
