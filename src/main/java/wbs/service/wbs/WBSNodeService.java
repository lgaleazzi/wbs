package wbs.service.wbs;

import wbs.model.wbs.WBSNode;
import wbs.model.wbs.elements.WBSElement;

import java.util.List;

public interface WBSNodeService {

    List<WBSNode> findAll();

    WBSNode findbyId(Long id);

    WBSNode create(WBSNode node);

    WBSNode createFromParentNode(WBSElement wbsElement, WBSNode parentNode);

    WBSNode edit(WBSNode node);

    void deleteById(Long id);
}
