package wbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wbs.model.wbs.elements.WBSElement;

@Repository
public interface WBSElementRepository extends JpaRepository<WBSElement, Long> {
}
