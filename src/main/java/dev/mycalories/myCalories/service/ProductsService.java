package dev.mycalories.myCalories.service;

import dev.mycalories.myCalories.dto.ProductView;
import dev.mycalories.myCalories.entity.Products;
import dev.mycalories.myCalories.entity.Users;

import java.util.List;

public interface ProductsService {
    Products saveProduct(String name,
                         String brand);

    void editProduct(Long id,
                     String name,
                     String brand,
                     Double protein,
                     Double fat,
                     Double carbohydrates,
                     Double alimentaryFiber,
                     Double kilocalorie);

    void delProduct(Long id);

    boolean checkProductExist(String name, String brand, Users user);

    List<ProductView> collectAllProducts();

    List<ProductView> collectMyProducts();
}
