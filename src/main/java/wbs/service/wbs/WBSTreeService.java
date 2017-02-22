package wbs.service.wbs;

import wbs.model.wbs.WBSTree;

import java.util.List;

/**
 * Created by livia on 09.01.17.
 */
public interface WBSTreeService {

    List<WBSTree> findAll();
    WBSTree findbyId(Long id);
    //TODO: find by project
    WBSTree create(WBSTree tree);
    WBSTree edit(WBSTree tree);
    void deleteById(Long id);
    void delete(WBSTree tree);
    //WBSTree buildRootNode(WBSTree tree);

}
