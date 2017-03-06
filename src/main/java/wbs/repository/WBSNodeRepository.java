package wbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wbs.model.wbs.WBSNode;


@Repository
public interface WBSNodeRepository extends JpaRepository<WBSNode, Long> {


}
