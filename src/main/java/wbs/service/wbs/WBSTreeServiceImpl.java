package wbs.service.wbs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wbs.model.wbs.WBSTree;
import wbs.repository.WBSTreeRepository;

import java.util.List;

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

    @Override
    public WBSTree create(WBSTree tree) {
        return wbsTreeRepository.save(tree);
    }

    @Override
    public WBSTree edit(WBSTree tree) {
        return wbsTreeRepository.save(tree);
    }

    @Override
    public void deleteById(Long id) {
        WBSTree tree = findbyId(id);
        wbsNodeService.deleteById(tree.getRoot().getId());
        wbsTreeRepository.delete(id);
    }

    @Override
    public void delete(WBSTree tree) {
        wbsTreeRepository.delete(tree);
    }
}
