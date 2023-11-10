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
    public void saveEnergy(Product product, Double protein, Double fat, Double carbohydrates, Double alimentaryFiber, Double kilocalorie) {
        EnergyValue energyValue = new EnergyValue();
        energyValue.setProduct(product);
        energyValue.setProtein(nonNullParam(protein));
        energyValue.setFat(nonNullParam(fat));
        energyValue.setCarbohydrates(nonNullParam(carbohydrates));
        energyValue.setAlimentaryFiber(nonNullParam(alimentaryFiber));
        energyValue.setKilocalorie(calcKilocalorie(energyValue));
        energyRepository.save(energyValue);
    }

    @Override
    public EnergyValue findByProduct(Product product) {
        Long productId = product.getId();
        return energyRepository.findById(productId).orElse(null);
    }

    @Override
    public Double calcKilocalorie(EnergyValue energyValue) {
        Double kilocalorie = energyValue.getKilocalorie();
        if (kilocalorie != null) {
            return kilocalorie;
        } else {
            kilocalorie = (energyValue.getProtein() + energyValue.getCarbohydrates()) * 4
                    + energyValue.getFat() * 9 + energyValue.getAlimentaryFiber() * 2;
        }
        BigDecimal round = BigDecimal.valueOf(kilocalorie).setScale(2, RoundingMode.HALF_UP);
        return round.doubleValue();
    }

    @Override
    public Double nonNullParam(Double param) {
        return param == null ? 0.0 : param;
    }
}
