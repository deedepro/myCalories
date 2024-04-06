package dev.mycalories.myCalories.repository;

import dev.mycalories.myCalories.entity.Food;
import dev.mycalories.myCalories.entity.Product;
import dev.mycalories.myCalories.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {
    Food findTopByProduct(Product product);
    Food findTopByRecipe(Recipe recipe);
}
