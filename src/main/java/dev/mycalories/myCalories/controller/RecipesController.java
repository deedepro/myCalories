package dev.mycalories.myCalories.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RecipesController {

    @GetMapping("/recipes")
    String showRecipePage(Model model){
        return "recipes/my_recipes";
    }

    @GetMapping("/recipe/add")
    String showAddRecipePage(Model model){
        return "recipe/add_recipe";
    }
}
