package dev.mycalories.myCalories.repository;

import dev.mycalories.myCalories.entity.EnergyValue;
import dev.mycalories.myCalories.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface EnergyRepository extends CrudRepository<EnergyValue, Long> {

}
