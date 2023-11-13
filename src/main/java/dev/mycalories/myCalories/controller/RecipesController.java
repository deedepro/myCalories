package dev.mycalories.myCalories.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RecipesController {

    @GetMapping("/recipes")
    String showRecipePage(Model model){
        return "recipes/recipes";
    }

    @PostMapping("/recipes/add")
    String showAddRecipePage(@RequestParam(value = "name") String name,
                             @RequestParam(value = "weight") int weight,
                             Model model){
        System.out.println(name);
        System.out.println(weight);
        return "recipes/edit_recipe";
    }
}
