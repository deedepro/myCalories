package dev.mycalories.myCalories.service;


import dev.mycalories.myCalories.dto.RecipeView;
import dev.mycalories.myCalories.entity.EnergyValue;
import dev.mycalories.myCalories.entity.Product;
import dev.mycalories.myCalories.entity.Recipe;

import java.math.BigInteger;
import java.util.List;

public interface RecipeService {

    List<RecipeView> collectAllRecipes();

    List<RecipeView> collectMyRecipes();

    Recipe createRecipe(String name, BigInteger weight, EnergyValue energyValue);

    Recipe editRecipe(Recipe recipe, String name, BigInteger count);

    Recipe findRecipe(long id);

    RecipeView createRecipeView(Recipe recipe);

    RecipeView createRecipeView(Recipe recipe, BigInteger weight);

    boolean isUserRecipe(Recipe recipe);

    void deleteRecipe(Recipe recipe);

    void recalculateEnergy(Recipe recipe);
}
