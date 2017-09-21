package wbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wbs.model.project.Project;

import java.util.List;


@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    //find all projects created by the current user
    @Query("select p from Project p where p.user.id=:#{principal.id}")
    List<Project> findAllForCurrentUser();

    //find a project with id created by the current user
    @Query("select p from Project p where p.user.id=:#{principal.id} and p.id=:#{#id}")
    Project findForCurrentUser(@Param("id") Long id);

}
