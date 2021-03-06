package wbs.service.wbs.elements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wbs.model.wbs.elements.WBSElement;
import wbs.repository.WBSElementRepository;

import java.util.List;

@Service
public class WBSElementServiceImpl implements WBSElementService {
    @Autowired
    private WBSElementRepository wbsElementRepository;

    @Override
    public List<WBSElement> findAll() {
        return wbsElementRepository.findAll();
    }

    @Override
    public WBSElement findbyId(Long id) {
        return wbsElementRepository.findOne(id);
    }

    @Override
    public WBSElement create(WBSElement element) {
        return wbsElementRepository.save(element);
    }

    @Override
    public WBSElement edit(WBSElement element) {
        return wbsElementRepository.save(element);
    }

    @Override
    public void deleteById(Long id) {
        wbsElementRepository.delete(id);
    }

    @Override
    public void delete(WBSElement element) {
        wbsElementRepository.delete(element);
    }
}
