package dev.mycalories.myCalories.service;

import dev.mycalories.myCalories.entity.Product;
import dev.mycalories.myCalories.entity.Recipe;

public interface IngridientService {

    void delIngredient(Recipe recipe, Product product);

    void addIngridient(Recipe recipe, Product product);
}
