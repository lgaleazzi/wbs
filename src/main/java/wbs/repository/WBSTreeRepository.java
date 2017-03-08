package wbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wbs.model.wbs.WBSTree;


@Repository
public interface WBSTreeRepository extends JpaRepository<WBSTree, Long> {
    //TODO: restrict access to authorized users
}
