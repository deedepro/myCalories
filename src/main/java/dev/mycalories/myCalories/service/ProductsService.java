package dev.mycalories.myCalories.service;

import dev.mycalories.myCalories.dto.ProductView;
import dev.mycalories.myCalories.entity.EnergyValue;
import dev.mycalories.myCalories.entity.Product;
import dev.mycalories.myCalories.entity.User;

import java.util.List;

public interface ProductsService {
    Product findProduct(Long id);
    Product createProduct(String name,
                          String brand,
                          EnergyValue energyValue);

    Product editProduct(Product product, String name, String brand, EnergyValue energyValue);

    boolean checkProductExist(String name, String brand, User user);

    List<ProductView> collectAllProducts();

    List<ProductView> collectMyProducts();

    ProductView makeProductView(Long productId);
}
