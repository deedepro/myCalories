package dev.mycalories.myCalories.service.impl;

import dev.mycalories.myCalories.entity.EnergyValue;
import dev.mycalories.myCalories.entity.Product;
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
    public void saveEnergy(Product product, String protein, String fat, String carbohydrates, String alimentaryFiber, String kilocalorie) {
        EnergyValue energyValue = new EnergyValue();
        energyValue.setProduct(product);
        energyValue.setProtein(nonNullParam(protein));
        energyValue.setFat(nonNullParam(fat));
        energyValue.setCarbohydrates(nonNullParam(carbohydrates));
        energyValue.setAlimentaryFiber(nonNullParam(alimentaryFiber));
        energyValue.setKilocalorie(kilocalorie.isEmpty()
                ? calcKilocalorie(energyValue)
                : Double.parseDouble(kilocalorie));
        energyRepository.save(energyValue);
    }

    @Override
    public EnergyValue findByProduct(Product product) {
        return energyRepository.findTopByProduct(product);
    }

    @Override
    public Double calcKilocalorie(EnergyValue energyValue) {
        BigDecimal result = BigDecimal.valueOf(
                (energyValue.getProtein() + energyValue.getCarbohydrates()) * 4
                        + energyValue.getFat() * 9 + energyValue.getAlimentaryFiber() * 2);
        return result.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    @Override
    public Double nonNullParam(String param) {
        return param.isEmpty() ? 0.0 : Double.parseDouble(param);
    }
}
