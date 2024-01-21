package dev.mycalories.myCalories.service.impl;

import dev.mycalories.myCalories.constants.Constants;
import dev.mycalories.myCalories.dto.ProductView;
import dev.mycalories.myCalories.dto.RecipeView;
import dev.mycalories.myCalories.entity.*;
import dev.mycalories.myCalories.repository.RecipeRepository;
import dev.mycalories.myCalories.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final UserService userService;
    private final FoodService foodService;
    private final EnergyService energyService;
    private final IngredientService ingredientService;

    @Autowired
    public RecipeServiceImpl(IngredientService ingredientService, RecipeRepository recipeRepository, UserService userService, FoodService foodService, EnergyService energyService, IngredientService ingredientService1) {
        this.recipeRepository = recipeRepository;
        this.userService = userService;
        this.foodService = foodService;
        this.energyService = energyService;
        this.ingredientService = ingredientService1;
    }

    @Override
    public List<RecipeView> collectAllRecipes() {
        Iterable<Recipe> allRecipes = recipeRepository.findAll();
        return createRecipeViews(allRecipes);
    }

    @Override
    public List<RecipeView> collectMyRecipes() {
        User currentUser = userService.getCurrentUser();
        Iterable<Recipe> allRecipes = recipeRepository.findAllByUser(currentUser);
        return createRecipeViews(allRecipes);
    }

    private List<RecipeView> createRecipeViews(Iterable<Recipe> recipes) {
        Iterator<Recipe> iterator = recipes.iterator();
        List<RecipeView> result = new ArrayList<>();
        while (iterator.hasNext()) {
            Recipe recipe = iterator.next();
            RecipeView recipeView = createRecipeView(recipe);
            result.add(recipeView);
        }
        return result;
    }

    @Override
    public RecipeView createRecipeView(Recipe recipe) {
        RecipeView recipeView = new RecipeView(recipe.getId(), recipe.getName(), recipe.getWeight());
        EnergyValue energyValue = recipe.getEnergyValue();
        recipeView.setEnergyValues(
                energyValue.getProtein(),
                energyValue.getFat(),
                energyValue.getCarbohydrates(),
                energyValue.getAlimentaryFiber(),
                energyValue.getKilocalorie()
        );
        recipeView.setUserProduct(isUserRecipe(recipe));
        return recipeView;
    }

    @Override
    public RecipeView createRecipeView(Recipe recipe, BigInteger weight) {
        RecipeView recipeView = new RecipeView(recipe.getId(), recipe.getName(), recipe.getWeight());
        EnergyValue energyValue = recipe.getEnergyValue();
        recipeView.setEnergyValues(
                energyService.calculateEnergyValueWeight(energyValue.getProtein(), weight),
                energyService.calculateEnergyValueWeight(energyValue.getFat(), weight),
                energyService.calculateEnergyValueWeight(energyValue.getCarbohydrates(), weight),
                energyService.calculateEnergyValueWeight(energyValue.getAlimentaryFiber(), weight),
                energyService.calcKilocalorie(energyValue, weight)
        );
        recipeView.setUserProduct(isUserRecipe(recipe));
        return recipeView;
    }

    /**
     * Проверка принадлежности продукта текущему пользователю
     *
     * @param recipe продукт
     * @return истина, если создатель продукта - текущий пользователь.
     */
    @Override
    public boolean isUserRecipe(Recipe recipe) {
        User currentUser = userService.getCurrentUser();
        return recipe.getUser().getId().equals(currentUser.getId());
    }

    @Override
    public Recipe createRecipe(String name, BigInteger weight, EnergyValue energyValue) {
        User user = userService.getCurrentUser();
        Recipe recipe = new Recipe(name, user, energyValue);
        if (weight != null) {
            recipe.setWeight(weight);
        }
        foodService.addRecipe(recipe);
        return recipe;
    }

    @Override
    public Recipe editRecipe(Recipe recipe, String name, BigInteger count) {
        User currentUser = userService.getCurrentUser();
        if (recipe.getUser().equals(currentUser)) {
            recipe.setName(name);
            recipe.setWeight(count);
            return recipe;
        }
        return recipe;

    }

    @Override
    public void deleteRecipe(Recipe recipe) {
        User currentUser = userService.getCurrentUser();
        if (recipe.getUser().equals(currentUser)) {
            recipeRepository.delete(recipe);
        }
    }

    @Override
    public void recalculateEnergy(Recipe recipe) {
        EnergyValue recipeEnergy = recipe.getEnergyValue();
        List<Ingredient> ingredients = ingredientService.collectIngredients(recipe);
        BigDecimal recipeProtein = Constants.ZERO;
        BigDecimal recipeFat = Constants.ZERO;
        BigDecimal recipeCarb = Constants.ZERO;
        BigDecimal recipeFibers = Constants.ZERO;
        BigDecimal recipeKcal = Constants.ZERO;
        BigInteger allIngredientsWeight = BigInteger.ZERO;
        for (Ingredient ingredient : ingredients) {
            EnergyValue productEnergy = ingredient.getProduct().getEnergyValue();
            BigInteger ingredientWeight = ingredient.getWeight();
            BigDecimal productProtein = energyService.calculateEnergyValueWeight(productEnergy.getProtein(), ingredientWeight);
            recipeProtein = recipeProtein.add(productProtein);
            BigDecimal productFat = energyService.calculateEnergyValueWeight(productEnergy.getFat(), ingredientWeight);
            recipeFat = recipeFat.add(productFat);
            BigDecimal productCarbohydrates = energyService.calculateEnergyValueWeight(productEnergy.getCarbohydrates(), ingredientWeight);
            recipeCarb = recipeCarb.add(productCarbohydrates);
            BigDecimal productAlimentaryFiber = energyService.calculateEnergyValueWeight(productEnergy.getAlimentaryFiber(), ingredientWeight);
            recipeFibers = recipeFibers.add(productAlimentaryFiber);
            BigDecimal productKilocalorie = energyService.calculateEnergyValueWeight(productEnergy.getKilocalorie(), ingredientWeight);
            recipeKcal = recipeKcal.add(productKilocalorie);
            allIngredientsWeight = allIngredientsWeight.add(ingredientWeight);
        }
        BigInteger weight = recipe.getWeight();
        if(weight == null){
            weight = allIngredientsWeight;
        }
        BigDecimal recipeWeight = new BigDecimal(weight);
        recipeEnergy.setProtein(energyService.calculateDefaultEnergyValueWeight(recipeProtein, recipeWeight));
        recipeEnergy.setFat(energyService.calculateDefaultEnergyValueWeight(recipeFat, recipeWeight));
        recipeEnergy.setCarbohydrates(energyService.calculateDefaultEnergyValueWeight(recipeCarb, recipeWeight));
        recipeEnergy.setAlimentaryFiber(energyService.calculateDefaultEnergyValueWeight(recipeFibers, recipeWeight));
        recipeEnergy.setKilocalorie(energyService.calculateDefaultEnergyValueWeight(recipeKcal, recipeWeight));
        energyService.saveEnergyValue(recipeEnergy);
    }

    @Override
    public Recipe findRecipe(long id) {
        return recipeRepository.findById(id).orElse(null);
    }
}
