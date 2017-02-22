package wbs.service.wbs.elements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wbs.repository.WBSElementRepository;
import wbs.model.wbs.elements.WBSElement;

import java.util.List;

//TODO: one implementation per element type? in this case will have to instantiate rather than autowire, is that bad?

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
