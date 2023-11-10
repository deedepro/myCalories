package dev.mycalories.myCalories.controller;

import dev.mycalories.myCalories.dto.ProductView;
import dev.mycalories.myCalories.entity.EnergyValue;
import dev.mycalories.myCalories.entity.Food;
import dev.mycalories.myCalories.entity.Product;
import dev.mycalories.myCalories.service.EnergyService;
import dev.mycalories.myCalories.service.FoodService;
import dev.mycalories.myCalories.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@Controller
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @Autowired
    private EnergyService energyService;

    @Autowired
    private FoodService foodService;

    @GetMapping("/products/all")
    String showAllProductsPage(Model model) {
        List<ProductView> allProducts = productsService.collectAllProducts();
        model.addAttribute("products", allProducts);
        return "products/all_products";
    }

    @GetMapping("/products/my")
    String showMyProductsPage(Model model) {
        List<ProductView> myProducts = productsService.collectMyProducts();
        model.addAttribute("products", myProducts);
        return "products/my_products";
    }

    @GetMapping("/products/add")
    String showAddPage(Model model) {
        return "products/add_product";
    }

    @GetMapping("/products/edit/{id}")
    String showEditPage(@PathVariable(value = "id") long id, Model model) {
        ProductView productView = productsService.makeProductView(id);
        if (Objects.nonNull(productView)) {
            model.addAttribute("product", productView);
        }
        return "products/edit_product";
    }

    @PostMapping("/products/add")
    String addProduct(@RequestParam String name, @RequestParam String brand, @RequestParam String protein, @RequestParam String fat, @RequestParam String carbs, @RequestParam String fibers, @RequestParam String kkal, Model model) {
        EnergyValue energyValue = energyService.createEnergyValue(protein, fat, carbs, fibers, kkal);
        Product product = productsService.createProduct(name, brand, energyValue);
        Food food = foodService.addProduct(product);
        String resultMessage = Objects.isNull(food) ? "Ошибка" : "Успешно добавлено";
        model.addAttribute("message", resultMessage);
        return "products/add_product";
    }

    @PostMapping("/products/edit/{id}")
    String editProduct(@PathVariable(value = "id") long id, @RequestParam String name, @RequestParam String brand, @RequestParam String protein, @RequestParam String fat, @RequestParam String carbs, @RequestParam String fibers, @RequestParam String kkal, Model model) {
        String message;
        String resultPath;
        Product product = productsService.findProduct(id);
        EnergyValue energyValue = product.getEnergyValue();
        energyValue = energyService.editEnergyValues(energyValue, protein, fat, carbs, fibers, kkal);
        product = productsService.editProduct(product, name, brand, energyValue);
        Food food = foodService.editProduct(product);
        if (Objects.isNull(food)) {
            message = "Не удалось изменить продукт";
            resultPath = "products/edit_product/" + id;
        } else {
            message = "Изменения успешно сохранены";
            resultPath = "redirect:/products/my";
        }
        model.addAttribute("message", message);
        return resultPath;
    }

    @PostMapping("/products/del/{id}")
    String deleteProduct(@PathVariable(value = "id") long id, Model model) {
        Product product = productsService.findProduct(id);
        foodService.delProduct(product);
        return "redirect:/products/my";
    }
}
