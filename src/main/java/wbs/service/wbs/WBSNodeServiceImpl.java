package wbs.service.wbs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wbs.exceptions.InvalidParentNodeException;
import wbs.model.wbs.elements.WBSElement;
import wbs.repository.WBSNodeRepository;
import wbs.model.wbs.WBSNode;
import wbs.service.wbs.elements.WBSElementService;

import java.util.LinkedList;
import java.util.List;

@Service
public class WBSNodeServiceImpl implements WBSNodeService {
    @Autowired
    private WBSNodeRepository wbsNodeRepository;

    @Autowired
    private WBSElementService wbsElementService;


    @Override
    public List<WBSNode> findAll() {
        return wbsNodeRepository.findAll();
    }

    @Override
    public WBSNode findbyId(Long id) {
        return wbsNodeRepository.findOne(id);
    }

    @Override
    public WBSNode create(WBSNode node) {
        if(node.hasParent() && !node.getParent().acceptsChildren()) {
            throw new InvalidParentNodeException("Parent node does not accept children");
        }
        return wbsNodeRepository.save(node);
    }

    @Override
    public WBSNode createFromParentNode(WBSElement wbsElement, WBSNode parentNode) {
        WBSNode newNode = new WBSNode(wbsElement, parentNode);
        newNode.setTree(parentNode.getTree());
        return create(newNode);
    }

    @Override
    public WBSNode edit(WBSNode node) {
        if(node.hasParent() && !node.getParent().acceptsChildren()) {
            throw new InvalidParentNodeException("Parent node does not accept children");
        }
        return wbsNodeRepository.save(node);
    }

    @Override
    public void deleteById(Long id) {
        wbsNodeRepository.delete(id);
    }
}
