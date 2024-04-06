package dev.mycalories.myCalories.service.impl;

import dev.mycalories.myCalories.entity.Portion;
import dev.mycalories.myCalories.entity.Product;
import dev.mycalories.myCalories.repository.PortionRepository;
import dev.mycalories.myCalories.service.PortionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PortionServiceImpl implements PortionService {
    private final PortionRepository portionRepository;

    @Autowired
    public PortionServiceImpl(PortionRepository portionRepository) {
        this.portionRepository = portionRepository;
    }

    @Override
    public void createDefaultPortion(Product product) {
        Portion portion = new Portion();
        portion.setName("гр.");
        portion.setCount(100);
        portion.setProduct(product);
        portionRepository.save(portion);
    }
}
