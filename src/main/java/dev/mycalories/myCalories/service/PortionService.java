package dev.mycalories.myCalories.service;

import dev.mycalories.myCalories.entity.Portion;
import dev.mycalories.myCalories.entity.Product;

import java.util.List;

public interface PortionService {
    Portion findPortionById(long id);
    void createDefaultPortion(Product product);
    void createPortion(Product product, String name, int count);
    void deletePortion(Portion portion);

    List<Portion> collectPortions(Product product);
}
