package dev.mycalories.myCalories.service;

import dev.mycalories.myCalories.entity.EnergyValues;
import dev.mycalories.myCalories.entity.Products;

public interface EnergyService {
    void saveEnergy(Products product,
                    String protein,
                    String fat,
                    String carbohydrates,
                    String alimentaryFiber,
                    String kilocalorie);
    Double calcKilocalorie(EnergyValues energyValues);
    Double nonNullParam(String param);
}
