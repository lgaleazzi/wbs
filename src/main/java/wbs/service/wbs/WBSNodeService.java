package wbs.service.wbs;

import wbs.model.wbs.WBSNode;

import java.util.List;

/**
 * Created by livia on 09.01.17.
 */
public interface WBSNodeService {

    List<WBSNode> findAll();
    WBSNode findbyId(Long id);
    WBSNode create(WBSNode node);
    WBSNode edit(WBSNode node);
    void deleteById(Long id);
}
