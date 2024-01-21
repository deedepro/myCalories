package dev.mycalories.myCalories.service;


import dev.mycalories.myCalories.entity.Food;
import dev.mycalories.myCalories.entity.Product;
import dev.mycalories.myCalories.entity.Recipe;

public interface FoodService {
    Food addProduct(Product product);

    Food addRecipe(Recipe recipe);

    Food editProduct(Product product);
    Food editRecipe(Recipe recipe);

    void delProduct(Product product);

    void delRecipe(Recipe recipe);

    Food findFood(Product product);

    Food findFood(Recipe recipe);
}
