package wbs.service.wbs;

import wbs.model.wbs.WBSTree;

import java.util.List;


public interface WBSTreeService {

    List<WBSTree> findAll();

    WBSTree findbyId(Long id);

    WBSTree create(WBSTree tree);

    WBSTree edit(WBSTree tree);

    void deleteById(Long id);

    void delete(WBSTree tree);

}
