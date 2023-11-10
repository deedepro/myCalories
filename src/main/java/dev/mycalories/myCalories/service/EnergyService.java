package dev.mycalories.myCalories.service;

import dev.mycalories.myCalories.entity.EnergyValue;
import dev.mycalories.myCalories.entity.Product;

public interface EnergyService {
    void saveEnergy(Product product,
                    String protein,
                    String fat,
                    String carbohydrates,
                    String alimentaryFiber,
                    String kilocalorie);

    EnergyValue findByProduct(Product product);
    Double calcKilocalorie(EnergyValue energyValue);
    Double nonNullParam(String param);

    EnergyValue editEnergyValues(Product product,
                          String protein,
                          String fat,
                          String carbohydrates,
                          String alimentaryFiber,
                          String kilocalorie);

    void delEnergyValue(Product product);
}
