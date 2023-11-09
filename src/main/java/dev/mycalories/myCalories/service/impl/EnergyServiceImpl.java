package dev.mycalories.myCalories.service.impl;

import dev.mycalories.myCalories.entity.EnergyValues;
import dev.mycalories.myCalories.entity.Products;
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
    public void saveEnergy(Products product, String protein, String fat, String carbohydrates, String alimentaryFiber, String kilocalorie) {
        EnergyValues energyValues = new EnergyValues();
        energyValues.setProduct(product);
        energyValues.setProtein(nonNullParam(protein));
        energyValues.setFat(nonNullParam(fat));
        energyValues.setCarbohydrates(nonNullParam(carbohydrates));
        energyValues.setAlimentaryFiber(nonNullParam(alimentaryFiber));
        energyValues.setKilocalorie(calcKilocalorie(energyValues));
        energyRepository.save(energyValues);
    }

    @Override
    public EnergyValues findByProduct(Products product) {
        Long productId = product.getId();
        return energyRepository.findById(productId).orElse(null);
    }

    @Override
    public Double calcKilocalorie(EnergyValues energyValues) {
        Double kilocalorie = energyValues.getKilocalorie();
        if (kilocalorie != null) {
            return kilocalorie;
        } else {
            kilocalorie = (energyValues.getProtein() + energyValues.getCarbohydrates()) * 4
                    + energyValues.getFat() * 9 + energyValues.getAlimentaryFiber() * 2;
        }
        BigDecimal round = BigDecimal.valueOf(kilocalorie).setScale(2, RoundingMode.HALF_UP);
        return round.doubleValue();
    }

    @Override
    public Double nonNullParam(String param) {
        try {
            return Double.parseDouble(param);
        } catch (Exception e) {
            return 0.0;
        }
    }
}
