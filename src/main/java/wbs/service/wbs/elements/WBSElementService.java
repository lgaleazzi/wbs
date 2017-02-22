package wbs.service.wbs.elements;

import wbs.model.wbs.elements.WBSElement;

import java.util.List;

/**
 * Created by livia on 09.01.17.
 */
public interface WBSElementService {

    List<WBSElement> findAll();
    WBSElement findbyId(Long id);
    WBSElement create(WBSElement element);
    WBSElement edit(WBSElement element);
    void deleteById(Long id);
    void delete(WBSElement element);
}
