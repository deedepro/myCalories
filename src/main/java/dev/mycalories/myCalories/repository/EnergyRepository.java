package dev.mycalories.myCalories.repository;

import dev.mycalories.myCalories.entity.EnergyValues;
import org.springframework.data.repository.CrudRepository;

public interface EnergyRepository extends CrudRepository<EnergyValues, Long> {
}
