package wbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wbs.model.project.Project;

import java.util.List;



@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("select p from Project p where p.user.id=:#{principal.id}")
    List<Project> findAll();

    @Query("select p from Project p where p.user.id=:#{principal.id} and p.id=:#{#id}")
    Project findForCurrentUser(@Param("id") Long id);
}
