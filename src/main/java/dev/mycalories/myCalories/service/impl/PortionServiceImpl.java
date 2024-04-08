package dev.mycalories.myCalories.service.impl;

import dev.mycalories.myCalories.entity.Portion;
import dev.mycalories.myCalories.entity.Product;
import dev.mycalories.myCalories.repository.PortionRepository;
import dev.mycalories.myCalories.service.PortionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortionServiceImpl implements PortionService {
    private final PortionRepository portionRepository;

    @Autowired
    public PortionServiceImpl(PortionRepository portionRepository) {
        this.portionRepository = portionRepository;
    }

    @Override
    public Portion findPortionById(long id) {
        return portionRepository.findById(id).orElse(null);
    }

    @Override
    public void createDefaultPortion(Product product) {
        Portion portion = new Portion();
        portion.setName("гр.");
        portion.setCount(100);
        portion.setProduct(product);
        portionRepository.save(portion);
    }

    @Override
    public void createPortion(Product product, String name, int count) {
        Portion portion = new Portion(product, name, count);
        portionRepository.save(portion);
    }

    @Override
    public void deletePortion(Portion portion) {
        portionRepository.delete(portion);
    }


    @Override
    public List<Portion> collectPortions(Product product) {
        return portionRepository.findAllByProduct(product);
    }
}
