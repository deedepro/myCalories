package dev.mycalories.myCalories.controller;

import dev.mycalories.myCalories.entity.Products;
import dev.mycalories.myCalories.service.EnergyService;
import dev.mycalories.myCalories.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @Autowired
    private EnergyService energyService;

    @GetMapping("/products/all")
    String showAllProductsPage(Model model) {
        return "products/all_products";
    }

    @GetMapping("/products/my")
    String showMyProductsPage(Model model){
        return "products/my_products";
    }

    @GetMapping("/products/add")
    String showAddPage(Model model){
        return "products/add_product";
    }

    @GetMapping("/products/edit")//TODO добавить id
    String showEditPage(Model model){
        return "products/edit_product";
    }

    @GetMapping("/products/del")//TODO добавить id
    String showDeletePage(Model model){
        return "products/del_product";
    }

    @PostMapping("/products/add")
    String addProduct(@RequestParam String name,
                      @RequestParam String brand,
                      @RequestParam String protein,
                      @RequestParam String fat,
                      @RequestParam String carbohydrates,
                      @RequestParam String alimentaryFiber,
                      @RequestParam String kilocalorie,
                      Model model){
        String resultMessage;
        Products product = productsService.saveProduct(name, brand);
        if(product != null){
            energyService.saveEnergy(product, protein, fat, carbohydrates, alimentaryFiber, kilocalorie);
            resultMessage = "успешно добавлено";
        }else {
            resultMessage = "продукт не был добавлен";
        }
        model.addAttribute("message",resultMessage);
        return "products/add_product";
    }

    @PostMapping("/products/edit")//TODO добавить id
    String editProduct(Model model){
        return null;
    }

    @PostMapping("/products/del")//TODO добавить id
    String deleteProduct(Model model){
        return null;
    }
}
