package dev.mycalories.myCalories.service;

import dev.mycalories.myCalories.entity.EnergyValue;
import dev.mycalories.myCalories.entity.Product;

public interface EnergyService {
    void saveEnergy(Product product,
                    Double protein,
                    Double fat,
                    Double carbohydrates,
                    Double alimentaryFiber,
                    Double kilocalorie);

    EnergyValue findByProduct(Product product);
    Double calcKilocalorie(EnergyValue energyValue);
    Double nonNullParam(Double param);
}
