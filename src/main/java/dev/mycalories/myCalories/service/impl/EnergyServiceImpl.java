package dev.mycalories.myCalories.service.impl;

import dev.mycalories.myCalories.constants.Constants;
import dev.mycalories.myCalories.entity.*;
import dev.mycalories.myCalories.repository.EnergyRepository;
import dev.mycalories.myCalories.service.EnergyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;

import static dev.mycalories.myCalories.constants.Constants.*;

@Service
public class EnergyServiceImpl implements EnergyService {

    private final EnergyRepository energyRepository;
    private final BigDecimal defaultWeight = HUNDRED;

    @Autowired
    public EnergyServiceImpl(EnergyRepository energyRepository) {
        this.energyRepository = energyRepository;
    }

    @Override
    public EnergyValue createEnergyValue(String protein, String fat, String carbs, String fibers, String kcal) {
        EnergyValue energyValue = new EnergyValue();
        return editEnergyValues(energyValue, protein, fat, carbs, fibers, kcal);
    }

    @Override
    public EnergyValue createEnergyValue() {
        EnergyValue energyValue = new EnergyValue();
        return editEnergyValues(energyValue, null, null, null, null, null);
    }

    @Override
    public BigDecimal calcKilocalorie(EnergyValue energyValue) {
        BigDecimal proteinKcal = energyValue.getProtein().multiply(FOUR);
        BigDecimal fatKcal = energyValue.getFat().multiply(NINE);
        BigDecimal carbKcal = energyValue.getCarbohydrates().multiply(FOUR);
        BigDecimal fibersKcal = energyValue.getAlimentaryFiber().multiply(TWO);
        return proteinKcal.add(fatKcal).add(carbKcal).add(fibersKcal).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal calcKilocalorie(EnergyValue energyValue, BigInteger weight) {
        return calcKilocalorie(energyValue)
                .multiply(new BigDecimal(weight))
                .divide(HUNDRED, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal calcKilocalorie(List<Diary> entities) {
        BigDecimal result = ZERO;
        for (Diary dayEntity : entities) {
            BigInteger weight = dayEntity.getWeight();
            EnergyValue energyValue;
            Product product = dayEntity.getFood().getProduct();
            if(product != null){
                energyValue = product.getEnergyValue();
            } else {
                energyValue = dayEntity.getFood().getRecipe().getEnergyValue();
            }
            BigDecimal kcal = calcKilocalorie(energyValue, weight);
            result = result.add(kcal);
        }
        return result;
    }

    @Override
    public BigDecimal calculateEnergyValueWeight(BigDecimal nutrientWeight, BigInteger entityWeight) {
        BigDecimal weight = new BigDecimal(entityWeight);
        BigDecimal result = nutrientWeight.multiply(weight).divide(defaultWeight, RoundingMode.HALF_UP);
        return result.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public void saveEnergyValue(EnergyValue energyValue) {
        energyRepository.save(energyValue);
    }

    private BigDecimal nonNullParam(String param) {
        return param == null || param.isEmpty() ? ZERO : new BigDecimal(param);
    }

    @Override
    public EnergyValue editEnergyValues(EnergyValue energyValue, String protein, String fat, String carbs, String fibers, String kcal) {
        energyValue.setProtein(nonNullParam(protein));
        energyValue.setFat(nonNullParam(fat));
        energyValue.setCarbohydrates(nonNullParam(carbs));
        energyValue.setAlimentaryFiber(nonNullParam(fibers));
        energyValue.setKilocalorie(kcal == null || kcal.isEmpty()
                ? this.calcKilocalorie(energyValue)
                : new BigDecimal(kcal));
        return energyValue;
    }

    @Override
    public BigDecimal calculateDefaultEnergyValueWeight(BigDecimal totalEnergyValue, BigDecimal totalWeight) {
        return totalEnergyValue.multiply(Constants.HUNDRED).divide(totalWeight, RoundingMode.HALF_UP);
    }


}
