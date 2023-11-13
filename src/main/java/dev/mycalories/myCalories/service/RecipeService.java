package dev.mycalories.myCalories.service;


import dev.mycalories.myCalories.entity.Recipe;

public interface RecipeService {
    Recipe createRecipe(String name);

    Recipe findRecipe(String name);
}
