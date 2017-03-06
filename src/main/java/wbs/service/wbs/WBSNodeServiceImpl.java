package wbs.service.wbs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wbs.model.wbs.elements.WBSElement;
import wbs.repository.WBSNodeRepository;
import wbs.model.wbs.WBSNode;
import wbs.service.wbs.elements.WBSElementService;

import java.util.List;

/**
 * Created by livia on 09.01.17.
 */
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
        return wbsNodeRepository.save(node);
    }

    @Override
    public WBSNode edit(WBSNode node) {
        return wbsNodeRepository.save(node);
    }

    @Override
    public void deleteById(Long id) {
        wbsNodeRepository.delete(id);
    }

}
