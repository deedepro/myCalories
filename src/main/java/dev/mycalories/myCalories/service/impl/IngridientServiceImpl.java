package dev.mycalories.myCalories.service.impl;

import dev.mycalories.myCalories.entity.Ingredient;
import dev.mycalories.myCalories.entity.Product;
import dev.mycalories.myCalories.entity.Recipe;
import dev.mycalories.myCalories.repository.IngredientsRepository;
import dev.mycalories.myCalories.service.IngridientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngridientServiceImpl implements IngridientService {

    @Autowired
    private IngredientsRepository ingredientRepo;

    @Override
    public void delIngredient(Recipe recipe, Product product) {
//        Ingredient ingredient = ingredientRepo.findTopByRecipeAndProduct(recipe, product);
//        ingredientRepo.delete(ingredient);
    }

    @Override
    public void addIngridient(Recipe recipe, Product product) {
//        Ingredient ingredient = new Ingredient(recipe, product);
//        ingredientRepo.save(ingredient);
    }
}
