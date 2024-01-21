package dev.mycalories.myCalories.service;

import dev.mycalories.myCalories.dto.IngredientView;
import dev.mycalories.myCalories.entity.Ingredient;
import dev.mycalories.myCalories.entity.Recipe;

import java.math.BigInteger;
import java.util.List;

public interface IngredientService {
    Ingredient fetch(Long id);
    List<IngredientView> collectIngredientsView(Recipe recipe);
    List<Ingredient> collectIngredients(Recipe recipe);

    IngredientView createIngredientView(Ingredient ingredient);

    void addIngredient(Recipe recipe, Long productId, BigInteger count);

    void editIngredient(Ingredient ingredient, BigInteger count);

    void deleteIngredient(Ingredient ingredient);
}
