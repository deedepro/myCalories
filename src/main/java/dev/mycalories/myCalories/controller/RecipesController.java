package dev.mycalories.myCalories.controller;

import dev.mycalories.myCalories.dto.IngredientView;
import dev.mycalories.myCalories.dto.ProductView;
import dev.mycalories.myCalories.dto.RecipeView;
import dev.mycalories.myCalories.entity.EnergyValue;
import dev.mycalories.myCalories.entity.Ingredient;
import dev.mycalories.myCalories.entity.Recipe;
import dev.mycalories.myCalories.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;
import java.util.List;

@Controller
public class RecipesController {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final ProductsController productsController;
    private final ProductsService productsService;
    private final FoodService foodService;
    private final EnergyService energyService;

    @Autowired
    public RecipesController(RecipeService recipeService, IngredientService ingredientService, ProductsController productsController, ProductsService productsService, FoodService foodService, EnergyService energyService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.productsController = productsController;
        this.productsService = productsService;
        this.foodService = foodService;
        this.energyService = energyService;
    }

    @GetMapping("/recipes")
    String showRecipesPage(Model model) {
        return showRecipesPage(model, "all");
    }

    @GetMapping("/recipes/{filter}")
    String showRecipesPage(Model model, @PathVariable(value = "filter") String filter) {
        return showRecipesPage(model, filter, 0);
    }

    @GetMapping("/recipes/{filter}/{id}")
    String showRecipesPage(Model model,
                           @PathVariable(value = "filter") String filter,
                           @PathVariable(value = "id") long selectedId) {
        model.addAttribute("filter", filter);

        List<RecipeView> recipes = collectRecipes(filter);
        if (!recipes.isEmpty()) {
            model.addAttribute("recipes", recipes);
        }
        RecipeView selectedRecipeView = loadSelectedRecipeInfo(recipes, selectedId);
        if (selectedRecipeView != null) {
            model.addAttribute("selectedRecipe", selectedRecipeView);
        }
        return "recipes";
    }

    @GetMapping("/recipes/add")
    String showAddRecipesPage(Model model) {
        return "recipe";
    }

    @GetMapping("/recipes/edit")
    String showEditRecipesPage(Model model, @RequestParam(value = "recipe_id") long recipeId) {
        Recipe recipe = recipeService.findRecipe(recipeId);
        model.addAttribute("recipe", recipe);
        return "recipe";
    }

    @GetMapping("/recipes/{id}/ingredients")
    String showIngredientsPage(@PathVariable(value = "id") Long recipeId,
                               Model model) {
        Recipe recipe = recipeService.findRecipe(recipeId);
        RecipeView recipeView = recipeService.createRecipeView(recipe);
        model.addAttribute("recipe", recipeView);

        List<IngredientView> ingredientViews = ingredientService.collectIngredientsView(recipe);
        model.addAttribute("ingredients", ingredientViews);

        return "ingredients";
    }

    @GetMapping("/recipes/ingredients/{id}")
    String showIngredientsPageWithSelect(@PathVariable(value = "id") Long ingredientId,
                                         Model model) {
        Ingredient ingredient = ingredientService.fetch(ingredientId);
        IngredientView ingredientView = ingredientService.createIngredientView(ingredient);
        model.addAttribute("selectedIngredient", ingredientView);

        Recipe recipe = ingredient.getRecipe();
        RecipeView recipeView = recipeService.createRecipeView(recipe);
        model.addAttribute("recipe", recipeView);

        List<IngredientView> ingredientViews = ingredientService.collectIngredientsView(recipe);
        model.addAttribute("ingredients", ingredientViews);

        return "ingredients";
    }

    @GetMapping("/recipes/menu")
    String showMenuPage(Model model,
                        @RequestParam(value = "recipe_id", required = false) Long recipeId) {
        return showMenuPage(model, null, null, recipeId);
    }

    @GetMapping("/recipes/menu/{filter}")
    String showMenuPage(Model model,
                        @PathVariable(value = "filter") String filter,
                        @RequestParam(name = "search", required = false) String search,
                        @RequestParam(value = "recipe_id", required = false) Long recipeId) {
        return showMenuPage(model, filter, 0, search, recipeId);
    }

    @GetMapping("/recipes/menu/{filter}/{id}")
    String showMenuPage(Model model,
                        @PathVariable(value = "filter") String filter,
                        @PathVariable(value = "id") long id,
                        @RequestParam(name = "search", required = false) String search,
                        @RequestParam(value = "recipe_id", required = false) Long recipeId) {
        model.addAttribute("recipeId", recipeId);
        productsController.prepareModel(model, filter, id, search);
        return "recipeProductPage";
    }

    @GetMapping("/recipes/menu/add/{product_id}")
    String showAddIngredientParamPage(Model model,
                                      @PathVariable(value = "product_id") Long productId,
                                      @RequestParam(value = "recipe_id", required = false) Long recipeId) {
        model.addAttribute("recipeId", recipeId);
        ProductView product = productsService.createProductView(productsService.findProduct(productId));
        model.addAttribute("product", product);
        return "addIngredientParams";
    }

    @GetMapping("/recipes/ingredient/edit")
    String showEditIngredientParamPage(Model model,
                                       @RequestParam(value = "ingredient_id") Long ingredientId,
                                       @RequestParam(value = "recipe_id", required = false) Long recipeId) {
        model.addAttribute("recipeId", recipeId);
        Ingredient ingredient = ingredientService.fetch(ingredientId);
        IngredientView ingredientView = ingredientService.createIngredientView(ingredient);
        model.addAttribute("ingredient", ingredientView);
        return "ingredient";
    }

    private RecipeView loadSelectedRecipeInfo(List<RecipeView> recipes, long selectedId) {
        if (selectedId != 0) {
            return recipes.stream().filter(recipe -> recipe.getId().equals(selectedId)).findAny().orElse(null);
        } else {
            return recipes.stream().findFirst().orElse(null);
        }
    }

    private List<RecipeView> collectRecipes(String filter) {
        if (filter.equals("my")) {
            return recipeService.collectMyRecipes();
//        TODO: доделать
//        } else if (filter.equals("favorites")) {
//            return recipeService.collectFavoritesRecipes();
        }
        return recipeService.collectAllRecipes();
    }

    @PostMapping("/recipes/create")
    String createRecipe(Model model,
                        @RequestParam(value = "name") String name,
                        @RequestParam(value = "weight", required = false) BigInteger weight) {
        EnergyValue energyValue = energyService.createEnergyValue();
        Recipe recipe = recipeService.createRecipe(name, weight, energyValue);
        String redirectUrl = "/recipes/" + recipe.getId() + "/ingredients";
        return "redirect:" + redirectUrl;
    }

    @PostMapping("/recipes/edit")
    String editRecipe(Model model,
                      @RequestParam(value = "recipe_id") long recipeId,
                      @RequestParam(value = "name") String name,
                      @RequestParam(value = "weight", required = false) BigInteger weight) {
        Recipe recipe = recipeService.findRecipe(recipeId);
        Recipe editRecipe = recipeService.editRecipe(recipe, name, weight);
        foodService.editRecipe(editRecipe);
        recipeService.recalculateEnergy(recipe);
        String redirectUrl = "/recipes/" + recipe.getId() + "/ingredients";
        return "redirect:" + redirectUrl;
    }

    @PostMapping("/recipes/del")
    String deleteRecipe(Model model,
                        @RequestParam(value = "recipe_id") long recipeId) {
        Recipe recipe = recipeService.findRecipe(recipeId);
        foodService.delRecipe(recipe);
        recipeService.deleteRecipe(recipe);
        String redirectUrl = "/recipes";
        return "redirect:" + redirectUrl;
    }

    @PostMapping("/recipes/ingredient/add")
    String addIngredient(Model model,
                         @RequestParam(value = "recipe_id") Long recipeId,
                         @RequestParam(value = "product_id") Long productId,
                         @RequestParam(value = "count") BigInteger count) {
        Recipe recipe = recipeService.findRecipe(recipeId);
        ingredientService.addIngredient(recipe, productId, count);
        recipeService.recalculateEnergy(recipe);
        String redirectUrl = "/recipes/" + recipeId + "/ingredients";
        return "redirect:" + redirectUrl;
    }

    @PostMapping("/recipes/ingredient/edit")
    String editIngredient(@RequestParam(value = "ingredient_id") Long ingredientId,
                          @RequestParam(value = "count") BigInteger count,
                          Model model) {
        Ingredient ingredient = ingredientService.fetch(ingredientId);
        ingredientService.editIngredient(ingredient, count);
        Recipe recipe = ingredient.getRecipe();
        recipeService.recalculateEnergy(recipe);
        String redirectUrl = "/recipes/" + ingredient.getRecipe().getId() + "/ingredients";
        return "redirect:" + redirectUrl;
    }

    @PostMapping("/recipes/ingredient/del")
    String deleteIngredient(Model model,
                            @RequestParam(value = "ingredient_id") Long id) {
        Ingredient ingredient = ingredientService.fetch(id);
        ingredientService.deleteIngredient(ingredient);
        Recipe recipe = ingredient.getRecipe();
        recipeService.recalculateEnergy(recipe);
        String redirectUrl = "/recipes/" + recipe.getId() + "/ingredients";
        return "redirect:" + redirectUrl;
    }

}
