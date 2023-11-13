package dev.mycalories.myCalories.service.impl;

import dev.mycalories.myCalories.dto.ProductView;
import dev.mycalories.myCalories.entity.EnergyValue;
import dev.mycalories.myCalories.entity.Product;
import dev.mycalories.myCalories.entity.User;
import dev.mycalories.myCalories.repository.ProductsRepository;
import dev.mycalories.myCalories.service.EnergyService;
import dev.mycalories.myCalories.service.ProductsService;
import dev.mycalories.myCalories.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;

@Service
public class ProductsServiceImpl implements ProductsService {

    @Autowired
    private UserService userService;

    @Autowired
    private EnergyService energyService;

    @Autowired
    private ProductsRepository productsRepository;

    @Override
    public Product findProduct(Long id) {
        return productsRepository.findById(id).orElse(null);
    }

    @Override
    public Product createProduct(String name, String brand, EnergyValue energyValue) {
        User currentUser = userService.getCurrentUser();
        boolean isProductExist = checkProductExist(name, brand, currentUser);
        if(isProductExist){
            return null;
        }
        return new Product(name,brand,currentUser,energyValue);
    }

    @Override
    public Product editProduct(Product product, String name, String brand, EnergyValue energyValue) {
        product.setName(name);
        product.setBrand(brand);
        product.setEnergyValue(energyValue);
        return product;

    }

    @Override
    public boolean checkProductExist(String name, String brand, User user) {
        return productsRepository.existsByNameAndBrandAndUser(name, brand, user);
    }

    @Override
    public List<ProductView> collectAllProducts() {
        Iterable<Product> allProducts = productsRepository.findAll();
        return createProductViews(allProducts);
    }

    @Override
    public List<ProductView> collectMyProducts() {
        User currentUser = userService.getCurrentUser();
        Assert.isTrue(currentUser != null, "Не найден текущий пользователь");
        Iterable<Product> allProducts = productsRepository.findAllByUser(currentUser);
        return createProductViews(allProducts);
    }

    @Override
    public ProductView makeProductView(Long productId) {
        Product product = productsRepository.findById(productId).orElse(null);
        return createProductViews(Collections.singletonList(product)).stream().findFirst().orElse(null);
    }

    private List<ProductView> createProductViews(Iterable<Product> products) {
        Iterator<Product> iterator = products.iterator();
        List<ProductView> result = new ArrayList<>();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            ProductView productView = new ProductView(product.getId(), product.getName(), product.getBrand());
            EnergyValue energyValue = product.getEnergyValue();
            productView.setEnergyValues(
                    energyValue.getProtein(),
                    energyValue.getFat(),
                    energyValue.getCarbohydrates(),
                    energyValue.getAlimentaryFiber(),
                    energyValue.getKilocalorie()
            );
            result.add(productView);
        }
        return result;
    }

    @Override
    public ProductView createProductView(Product product) {
        ProductView productView = new ProductView(product.getId(), product.getName(), product.getBrand());
        EnergyValue energyValue = product.getEnergyValue();
        productView.setEnergyValues(
                energyValue.getProtein(),
                energyValue.getFat(),
                energyValue.getCarbohydrates(),
                energyValue.getAlimentaryFiber(),
                energyValue.getKilocalorie()
        );
        return productView;
    }


        private Product makeProduct(String name, String brand, User user) {
        boolean isProductExist = checkProductExist(name, brand, user);
        if (isProductExist) {
            return null;
        } else {
            Product product = new Product();
            product.setName(name);
            product.setBrand(brand);
            product.setUser(user);
            return product;
        }
    }
}
