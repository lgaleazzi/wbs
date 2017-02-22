package wbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import wbs.model.project.Project;

import java.util.List;

/**
 * implemented automatically by Spring
 */

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("select p from Project p where p.user.id=:#{principal.id}")
    List<Project> findAll();
}
