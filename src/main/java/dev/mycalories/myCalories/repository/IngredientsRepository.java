package dev.mycalories.myCalories.repository;

import dev.mycalories.myCalories.entity.Ingredient;
import dev.mycalories.myCalories.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientsRepository extends JpaRepository<Ingredient, Long> {
    Ingredient findTopById(Long id);
    List<Ingredient> findAllByRecipe(Recipe recipe);
}
