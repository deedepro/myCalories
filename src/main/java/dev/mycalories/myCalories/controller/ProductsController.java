package dev.mycalories.myCalories.controller;

import dev.mycalories.myCalories.dto.ProductView;
import dev.mycalories.myCalories.entity.EnergyValue;
import dev.mycalories.myCalories.entity.Portion;
import dev.mycalories.myCalories.entity.Product;
import dev.mycalories.myCalories.service.EnergyService;
import dev.mycalories.myCalories.service.FoodService;
import dev.mycalories.myCalories.service.PortionService;
import dev.mycalories.myCalories.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

/**
 * Класс отвечает за работу сайта по пути /products
 */
@Controller
public class ProductsController {
    private final ProductsService productsService;
    private final PortionService portionService;
    private final EnergyService energyService;
    private final FoodService foodService;

    @Autowired
    public ProductsController(ProductsService productsService, PortionService portionService, EnergyService energyService, FoodService foodService) {
        this.productsService = productsService;
        this.portionService = portionService;
        this.energyService = energyService;
        this.foodService = foodService;
    }

    /**
     * Обработчик события открытия страницы "Продукты" с учетом фильтра и выбранного продукта
     *
     * @param model параметры страницы
     * @return открытие страницы "Продукты"
     */
    @GetMapping("/products")
    String showProductsPage(Model model,
                            @RequestParam(value = "id", required = false) Long id,
                            @RequestParam(value = "filter", required = false) String filter,
                            @RequestParam(name = "search", required = false) String search) {
        prepareModel(model, filter, id, search);
        return "products";
    }

    /**
     * Обработчик события открытия страницы "Продукты" в режиме добавления продукта
     *
     * @param model параметры страницы
     * @return открытие страницы "Продукты"
     */
    @GetMapping("/product/add")
    String showAddPage(Model model) {
        return "product";
    }

    /**
     * Обработчик события открытия страницы "Продукты" в режиме изменений продукта
     *
     * @param id    id изменяемого продукта
     * @param model параметры страницы
     * @return открытие страницы "Продукты"
     */
    @GetMapping("/product/edit/{id}")
    String showEditPage(Model model,
                        @PathVariable(value = "id") long id,
                        @RequestParam(value = "selectedFilter") String selectedFilter) {
        ProductView productView = productsService.makeProductView(id);
        model.addAttribute("selectedId", id);
        model.addAttribute("selectedFilter", selectedFilter);
        model.addAttribute("product", productView);
        return "product";
    }

    void prepareModel(Model model, String filter, Long id, String search) {
        if(filter == null) filter = "all";
        model.addAttribute("filter", filter);
        List<ProductView> products = Collections.emptyList();
        switch (filter) {
            case "all":
                products = productsService.collectAllProducts();
                break;
            case "my":
                products = productsService.collectMyProducts();
                break;
            case "favorites":
//            TODO: доделать "Избранное"
//            products = productsService.collectFavoriteProducts();
                break;
        }

        //search
        if (search != null && !search.isEmpty()) {
            products = productsService.searchProducts(products, search);
            model.addAttribute("search", search);
        }

        model.addAttribute("products", products);
        ProductView selectedProductView;
        if (id != null) {
            selectedProductView = products.stream().filter(product -> product.getId().equals(id)).findAny().orElse(null);
        } else {
            selectedProductView = products.stream().findFirst().orElse(null);
        }
        model.addAttribute("selectedProduct", selectedProductView);
    }

    private String buildUrlParams(long id, String filter) {
        StringBuilder sb = new StringBuilder();
        if (filter != null) {
            sb.append("/").append(filter);
        }
        if (id != 0) {
            sb.append("/").append(id);
        }
        return sb.toString();
    }

    /**
     * Обработчик события добавления нового продукта в систему
     *
     * @param name    имя продукта
     * @param brand   производитель продукта
     * @param protein белки
     * @param fat     жиры
     * @param carb    углеводы
     * @param fibers  пищевые волокна
     * @param kcal    энергетическая ценность в ккал
     * @param model   параметры страницы
     * @return переадресация на страницу "Продукты"
     */
    @PostMapping("/product/add")
    String addProduct(@RequestParam String name, @RequestParam String brand, @RequestParam String protein, @RequestParam String fat, @RequestParam String carb, @RequestParam String fibers, @RequestParam(required = false) String kcal, Model model) {
        EnergyValue energyValue = energyService.createEnergyValue(protein, fat, carb, fibers, kcal);
        Product product = productsService.createProduct(name, brand, energyValue);
        portionService.createDefaultPortion(product);
        foodService.addProduct(product);
        return "redirect:/products";
    }

    /**
     * Обработчик события изменения продукта в системе
     *
     * @param name    новое значение параметра имя продукта
     * @param brand   новое значение параметра производитель продукта
     * @param protein новое значение параметра белки
     * @param fat     новое значение параметра жиры
     * @param carb    новое значение параметра углеводы
     * @param fibers  новое значение параметра пищевые волокна
     * @param kcal    новое значение параметра энергетическая ценность в ккал
     * @param model   параметры страницы
     * @return переадресация на страницу "Продукты"
     */
    @PostMapping("/product/edit/{id}")
    String editProduct(Model model,
                       @PathVariable(value = "id") long id,
                       @RequestParam String name,
                       @RequestParam String brand,
                       @RequestParam String protein,
                       @RequestParam String fat,
                       @RequestParam String carb,
                       @RequestParam String fibers,
                       @RequestParam(required = false) String kcal,
                       @RequestParam(required = false) long selectedId,
                       @RequestParam(required = false) String selectedFilter) {
        Product product = productsService.findProduct(id);
        if (productsService.isUserProduct(product)) {
            EnergyValue energyValue = product.getEnergyValue();
            energyValue = energyService.editEnergyValues(energyValue, protein, fat, carb, fibers, kcal);
            product = productsService.editProduct(product, name, brand, energyValue);
            foodService.editProduct(product);
        }

        String urlParams = buildUrlParams(selectedId, selectedFilter);
//        return "redirect:/products" + urlParams;
        return "redirect:/products/" + id + "/portions?selectedFilter=" + selectedFilter;
    }

    /**
     * Обработчик события удаления продукта из системы
     *
     * @param id    id продукта
     * @param model параметры страницы
     * @return переадресация на страницу "Продукты"
     */
    @PostMapping("/product/del/{id}")
    String deleteProduct(Model model,
                         @PathVariable(value = "id") long id,
                         @RequestParam(required = false) String selectedFilter) {
        Product product = productsService.findProduct(id);
        if (productsService.isUserProduct(product)) {
            foodService.delProduct(product);
        }
        String urlParams = buildUrlParams(0, selectedFilter);
        return "redirect:/products" + urlParams;
    }
}
