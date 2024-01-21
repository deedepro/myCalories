package dev.mycalories.myCalories.service;

import dev.mycalories.myCalories.entity.Diary;
import dev.mycalories.myCalories.entity.EnergyValue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public interface EnergyService {

    EnergyValue createEnergyValue(String protein,
                                  String fat,
                                  String carbohydrates,
                                  String alimentaryFiber,
                                  String kilocalorie);
    EnergyValue createEnergyValue();

    EnergyValue editEnergyValues(EnergyValue energyValue,
                                 String protein,
                                 String fat,
                                 String carbs,
                                 String fibers,
                                 String kcal);


    BigDecimal calcKilocalorie(List<Diary> entries);

    BigDecimal calcKilocalorie(EnergyValue energyValue);

    BigDecimal calcKilocalorie(EnergyValue energyValue, BigInteger weight);

    BigDecimal calculateEnergyValueWeight(BigDecimal nutrientWeight, BigInteger entityWeight);
    BigDecimal calculateDefaultEnergyValueWeight(BigDecimal totalEnergyValue, BigDecimal totalWeight);

    void saveEnergyValue(EnergyValue energyValue);
}
