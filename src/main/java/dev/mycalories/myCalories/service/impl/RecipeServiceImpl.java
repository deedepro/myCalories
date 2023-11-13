package dev.mycalories.myCalories.service.impl;

import dev.mycalories.myCalories.entity.Recipe;
import dev.mycalories.myCalories.repository.RecipeRepository;
import dev.mycalories.myCalories.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RecipeRepository recipeRepo;

    @Override
    public Recipe createRecipe(String name) {
//        return new Recipe(name);
        return null;
    }

    @Override
    public Recipe findRecipe(String name) {
        return recipeRepo.findTopByName(name);
    }
}
