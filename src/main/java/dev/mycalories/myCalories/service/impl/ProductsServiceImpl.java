package dev.mycalories.myCalories.service.impl;

import dev.mycalories.myCalories.entity.Products;
import dev.mycalories.myCalories.entity.Users;
import dev.mycalories.myCalories.repository.ProductsRepository;
import dev.mycalories.myCalories.service.EnergyService;
import dev.mycalories.myCalories.service.ProductsService;
import dev.mycalories.myCalories.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class ProductsServiceImpl implements ProductsService {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private EnergyService energyService;

    @Autowired
    private ProductsRepository productsRepository;

    @Override
    public Products saveProduct(String name, String brand) {
        Users currentUser = registrationService.getCurrentUser();
        Assert.isTrue(currentUser != null, "Не найден текущий пользователь");
        Products product = makeProduct(name, brand, currentUser);
        if (product != null) {
            productsRepository.save(product);
            return product;
        } else {
            return null;
        }
    }

    @Override
    public void editProduct(Long id, String name, String brand, Double protein, Double fat, Double carbohydrates, Double alimentaryFiber, Double kilocalorie) {

    }

    @Override
    public void delProduct(Long id) {

    }

    @Override
    public boolean checkProductExist(String name, String brand, Users user) {
        return productsRepository.existsByNameAndBrandAndUser(name, brand, user);
    }

    private Products makeProduct(String name, String brand, Users user) {
        boolean isProductExist = checkProductExist(name, brand, user);
        if (isProductExist) {
            return null;
        } else {
            Products products = new Products();
            products.setName(name);
            products.setBrand(brand);
            products.setUser(user);
            return products;
        }
    }
}
