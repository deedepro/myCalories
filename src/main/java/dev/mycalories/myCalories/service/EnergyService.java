package dev.mycalories.myCalories.service;

import dev.mycalories.myCalories.entity.EnergyValue;

public interface EnergyService {

    EnergyValue createEnergyValue(String protein,
                                  String fat,
                                  String carbohydrates,
                                  String alimentaryFiber,
                                  String kilocalorie);

    EnergyValue editEnergyValues(EnergyValue energyValue,
                                 String protein,
                                 String fat,
                                 String carbs,
                                 String fibers,
                                 String kcal);

    Double calcKcal(EnergyValue energyValue);
}
