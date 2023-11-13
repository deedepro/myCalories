package dev.mycalories.myCalories.repository;

import dev.mycalories.myCalories.entity.Ingredient;
import dev.mycalories.myCalories.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientsRepository extends JpaRepository<Ingredient, Long> {
}
