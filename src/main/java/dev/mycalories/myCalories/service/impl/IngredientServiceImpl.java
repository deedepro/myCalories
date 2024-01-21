package dev.mycalories.myCalories.service.impl;

import dev.mycalories.myCalories.dto.IngredientView;
import dev.mycalories.myCalories.dto.ProductView;
import dev.mycalories.myCalories.entity.Ingredient;
import dev.mycalories.myCalories.entity.Product;
import dev.mycalories.myCalories.entity.Recipe;
import dev.mycalories.myCalories.repository.IngredientsRepository;
import dev.mycalories.myCalories.service.IngredientService;
import dev.mycalories.myCalories.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientsRepository ingredientRepo;
    private final ProductsService productsService;

    @Autowired
    public IngredientServiceImpl(IngredientsRepository ingredientRepo, ProductsService productsService) {
        this.ingredientRepo = ingredientRepo;
        this.productsService = productsService;
    }

    @Override
    public Ingredient fetch(Long id) {
        return ingredientRepo.findTopById(id);
    }

    @Override
    public List<IngredientView> collectIngredientsView(Recipe recipe) {
        List<Ingredient> ingredients = collectIngredients(recipe);
        List<IngredientView> result = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            IngredientView ingredientView = createIngredientView(ingredient);
            result.add(ingredientView);
        }
        return result;
    }

    @Override
    public List<Ingredient> collectIngredients(Recipe recipe) {
        return ingredientRepo.findAllByRecipe(recipe);
    }

    @Override
    public IngredientView createIngredientView(Ingredient ingredient) {
        Product product = ingredient.getProduct();
        ProductView productView = productsService.createProductView(product, ingredient.getWeight());
        return new IngredientView(ingredient.getId(),
                ingredient.getWeight(),
                productView);
    }

    @Override
    public void deleteIngredient(Ingredient ingredient) {
        ingredientRepo.delete(ingredient);
    }

    @Override
    public void addIngredient(Recipe recipe, Long productId, BigInteger count) {
        Product product = productsService.findProduct(productId);
        Ingredient ingredient = new Ingredient(recipe, product, count);
        ingredientRepo.save(ingredient);
    }

    @Override
    public void editIngredient(Ingredient ingredient, BigInteger count) {
        ingredient.setWeight(count);
        ingredientRepo.save(ingredient);
    }
}
