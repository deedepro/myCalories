package dev.mycalories.myCalories.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductsController {
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
    String addProduct(Model model){
        return null;
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
