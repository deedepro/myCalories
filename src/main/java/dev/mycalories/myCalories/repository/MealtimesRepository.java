package dev.mycalories.myCalories.repository;

import dev.mycalories.myCalories.entity.Mealtime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealtimesRepository extends JpaRepository<Mealtime, Long> {
}
