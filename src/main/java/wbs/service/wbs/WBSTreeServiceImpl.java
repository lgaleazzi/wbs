package wbs.service.wbs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wbs.repository.WBSTreeRepository;
import wbs.model.wbs.WBSTree;

import java.util.List;

/**
 * Created by livia on 09.01.17.
 */
@Service
public class WBSTreeServiceImpl implements WBSTreeService {

    @Autowired
    private WBSTreeRepository wbsTreeRepository;

    @Autowired
    private WBSNodeService wbsNodeService;


    @Override
    public List<WBSTree> findAll() {
        return wbsTreeRepository.findAll();
    }

    @Override
    public WBSTree findbyId(Long id) {
        return wbsTreeRepository.findOne(id);
    }

    //TODO: find by project

    @Override
    public WBSTree create(WBSTree tree) {
        //TODO: check if the tree has a root
        return wbsTreeRepository.save(tree);
    }

    @Override
    public WBSTree edit(WBSTree tree) {
        //TODO: check if the tree still has a root
        return wbsTreeRepository.save(tree);
    }

    @Override
    public void deleteById(Long id) {
        WBSTree tree = findbyId(id);
        //TODO: delete all nodes for this tree
        //The tree can delete all its children nodes, I just need to persist afterwards. how do I do that?

        wbsTreeRepository.delete(id);
    }

    @Override
    public void delete(WBSTree tree) {
        wbsTreeRepository.delete(tree);
    }
}
