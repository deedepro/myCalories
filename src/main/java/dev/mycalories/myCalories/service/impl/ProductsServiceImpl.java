package dev.mycalories.myCalories.service.impl;

import dev.mycalories.myCalories.dto.ProductView;
import dev.mycalories.myCalories.entity.EnergyValue;
import dev.mycalories.myCalories.entity.Product;
import dev.mycalories.myCalories.entity.User;
import dev.mycalories.myCalories.repository.ProductsRepository;
import dev.mycalories.myCalories.service.EnergyService;
import dev.mycalories.myCalories.service.ProductsService;
import dev.mycalories.myCalories.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ProductsServiceImpl implements ProductsService {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private EnergyService energyService;

    @Autowired
    private ProductsRepository productsRepository;

    @Override
    public Product saveProduct(String name, String brand) {
        User currentUser = registrationService.getCurrentUser();
        Assert.isTrue(currentUser != null, "Не найден текущий пользователь");
        Product product = makeProduct(name, brand, currentUser);
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
        User currentUser = registrationService.getCurrentUser();
        Assert.isTrue(currentUser != null, "Не найден текущий пользователь");
        Iterable<Product> allProducts = productsRepository.findAllByUser(currentUser);
        return createProductViews(allProducts);
    }

    private List<ProductView> createProductViews(Iterable<Product> products){
        Iterator<Product> iterator = products.iterator();
        List<ProductView> result = new ArrayList<>();
        while(iterator.hasNext()){
            Product product = iterator.next();
            ProductView productView = new ProductView(product.getId(), product.getName(),product.getBrand());
            EnergyValue energyValue = energyService.findByProduct(product);
            if(energyValue == null){
                continue;
            }
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
