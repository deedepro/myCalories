package dev.mycalories.myCalories.service.impl;


import dev.mycalories.myCalories.entity.Food;
import dev.mycalories.myCalories.entity.Product;
import dev.mycalories.myCalories.entity.Recipe;
import dev.mycalories.myCalories.repository.FoodRepository;
import dev.mycalories.myCalories.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepository foodRepo;

    @Override
    public Food addProduct(Product product) {
        Food food = new Food(product);
        foodRepo.save(food);
        return food;
    }

    @Override
    public void addRecipe(Recipe recipe) {
        Food food = new Food(recipe);
        foodRepo.save(food);
    }

    @Override
    public Food editProduct(Product product) {
        Food food = findFood(product);
        foodRepo.save(food);
        return food;
    }

    @Override
    public Food editRecipe(Recipe recipe) {
        return null;
    }

    @Override
    public void delProduct(Product product) {
        Food food = foodRepo.findTopByProduct(product);
        foodRepo.delete(food);
    }

    @Override
    public void delRecipe(Recipe recipe) {
        Food food = foodRepo.findTopByRecipe(recipe);
        foodRepo.delete(food);
    }

    @Override
    public Food findFood(Product product) {
        return foodRepo.findTopByProduct(product);
    }

    @Override
    public Food findFood(Recipe recipe) {
        return foodRepo.findTopByRecipe(recipe);
    }
}
