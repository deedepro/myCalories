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

/**
 * Класс отвечает за работу сайта по пути /products
 */
@Controller
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @Autowired
    private EnergyService energyService;

    @Autowired
    private FoodService foodService;

    /**
     * Обработчик события открытия страницы "Продукты"
     * @param model параметры страницы
     * @return открытие страницы "Продукты"
     */
    @GetMapping("/products")
    String showProductsPage(Model model){
        List<ProductView> products = productsService.collectAllProducts();
        model.addAttribute("products", products);
        model.addAttribute("collection", "all");
        return "products/products";
    }

    /**
     * Обработчик события открытия вкладки страницы "Мои продукты"
     * @param model параметры страницы
     * @return открытия вкладки страницы "Мои продукты"
     */
    @GetMapping("/products/my")
    String showMyProductsPage(Model model){
        List<ProductView> products = productsService.collectMyProducts();
        model.addAttribute("products", products);
        model.addAttribute("collection", "my");
        return "products/products";
    }

    /**
     * Обработчик события добавления нового продукта в систему
     * @param name имя продукта
     * @param brand производитель продукта
     * @param protein белки
     * @param fat жиры
     * @param carbs углеводы
     * @param fibers пищевые волокна
     * @param kcal энергетическая ценность в ккал
     * @param model параметры страницы
     * @return переадресация на страницу "Продукты"
     */
    @PostMapping("/products/add")
    String addProduct(@RequestParam String name, @RequestParam String brand, @RequestParam String protein, @RequestParam String fat, @RequestParam String carbs, @RequestParam String fibers, @RequestParam String kcal, Model model) {
        EnergyValue energyValue = energyService.createEnergyValue(protein, fat, carbs, fibers, kcal);
        Product product = productsService.createProduct(name, brand, energyValue);
        foodService.addProduct(product);
        return "redirect:/products";
    }

    /**
     * Обработчик события открытия страницы "Продукты" в режиме изменений продукта
     * @param id id изменяемого продукта
     * @param model параметры страницы
     * @return открытие страницы "Продукты"
     */
    @GetMapping("/products/edit/{id}")
    String showEditPage(@PathVariable(value = "id") long id, Model model) {
        model.addAttribute("edit",true);
        ProductView productView = productsService.makeProductView(id);
        model.addAttribute("product", productView);
        return showProductsPage(model);
    }

    /**
     * Обработчик события изменения продукта в системе
     * @param name новое значение параметра имя продукта
     * @param brand новое значение параметра производитель продукта
     * @param protein новое значение параметра белки
     * @param fat новое значение параметра жиры
     * @param carbs новое значение параметра углеводы
     * @param fibers новое значение параметра пищевые волокна
     * @param kcal новое значение параметра энергетическая ценность в ккал
     * @param model параметры страницы
     * @return переадресация на страницу "Продукты"
     */
    @PostMapping("/products/edit/{id}")
    String editProduct(@PathVariable(value = "id") long id, @RequestParam String name, @RequestParam String brand, @RequestParam String protein, @RequestParam String fat, @RequestParam String carbs, @RequestParam String fibers, @RequestParam String kcal, Model model) {
        Product product = productsService.findProduct(id);
        EnergyValue energyValue = product.getEnergyValue();
        energyValue = energyService.editEnergyValues(energyValue, protein, fat, carbs, fibers, kcal);
        product = productsService.editProduct(product, name, brand, energyValue);
        foodService.editProduct(product);
        return "redirect:/products";
    }

    /**
     * Обработчик события удаления продукта из системы
     * @param id id продукта
     * @param model параметры страницы
     * @return переадресация на страницу "Продукты"
     */
    @PostMapping("/products/del/{id}")
    String deleteProduct(@PathVariable(value = "id") long id, Model model) {
        Product product = productsService.findProduct(id);
        foodService.delProduct(product);
        return "redirect:/products";
    }
}
