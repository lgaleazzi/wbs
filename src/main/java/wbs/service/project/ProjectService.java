package wbs.service.project;

import wbs.model.project.Project;

import java.util.List;

/**
 *
 */
public interface ProjectService {

    List<Project> findAllForCurrentUser();

    Project findbyId(Long id);

    Project create(Project project);

    Project edit(Project project);

    void deleteById(Long id);

    boolean currentUserIsAuthorized(Long id);

}
