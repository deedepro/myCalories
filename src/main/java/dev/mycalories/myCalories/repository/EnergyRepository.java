package dev.mycalories.myCalories.repository;

import dev.mycalories.myCalories.entity.EnergyValue;
import org.springframework.data.repository.CrudRepository;

public interface EnergyRepository extends CrudRepository<EnergyValue, Long> {
}
