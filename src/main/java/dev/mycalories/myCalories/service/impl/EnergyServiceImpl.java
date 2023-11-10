package dev.mycalories.myCalories.service.impl;

import dev.mycalories.myCalories.entity.EnergyValue;
import dev.mycalories.myCalories.repository.EnergyRepository;
import dev.mycalories.myCalories.service.EnergyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class EnergyServiceImpl implements EnergyService {
    @Autowired
    private EnergyRepository energyRepository;

    @Override
    public EnergyValue createEnergyValue(String protein, String fat, String carbs, String fibers, String kkal) {
        EnergyValue energyValue = new EnergyValue();
        return editEnergyValues(energyValue, protein, fat, carbs, fibers, kkal);
    }

    @Override
    public Double calcKkal(EnergyValue energyValue) {
        BigDecimal result = BigDecimal.valueOf(
                (energyValue.getProtein() + energyValue.getCarbohydrates()) * 4
                        + energyValue.getFat() * 9 + energyValue.getAlimentaryFiber() * 2);
        return result.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    private Double nonNullParam(String param) {
        return param.isEmpty() ? 0.0 : Double.parseDouble(param);
    }

    @Override
    public EnergyValue editEnergyValues(EnergyValue energyValue, String protein, String fat, String carbs, String fibers, String kkal) {
        energyValue.setProtein(nonNullParam(protein));
        energyValue.setFat(nonNullParam(fat));
        energyValue.setCarbohydrates(nonNullParam(carbs));
        energyValue.setAlimentaryFiber(nonNullParam(fibers));
        energyValue.setKilocalorie(kkal.isEmpty()
                ? calcKkal(energyValue)
                : Double.parseDouble(kkal));
        return energyValue;
    }
}
