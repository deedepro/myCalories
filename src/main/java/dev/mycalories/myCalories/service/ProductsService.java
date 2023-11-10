package dev.mycalories.myCalories.service;

import dev.mycalories.myCalories.dto.ProductView;
import dev.mycalories.myCalories.entity.Product;
import dev.mycalories.myCalories.entity.User;

import java.util.List;

public interface ProductsService {
    Product saveProduct(String name,
                        String brand);

    Product editProduct(Long id,
                     String name,
                     String brand);

    void delProduct(Long id);

    boolean checkProductExist(String name, String brand, User user);

    List<ProductView> collectAllProducts();

    List<ProductView> collectMyProducts();

    ProductView makeProductView(Long productId);
}
