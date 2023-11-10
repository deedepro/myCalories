package dev.mycalories.myCalories.service;

import dev.mycalories.myCalories.entity.EnergyValues;
import dev.mycalories.myCalories.entity.Products;

public interface EnergyService {
    void saveEnergy(Products product,
                    Double protein,
                    Double fat,
                    Double carbohydrates,
                    Double alimentaryFiber,
                    Double kilocalorie);

    EnergyValues findByProduct(Products product);
    Double calcKilocalorie(EnergyValues energyValues);
    Double nonNullParam(Double param);
}
