package dev.mycalories.myCalories.repository;

import dev.mycalories.myCalories.entity.Product;
import dev.mycalories.myCalories.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends CrudRepository<Product, Long> {
    Product findTopByOrderByIdDesc();

    Iterable<Product> findAllByUser(User user);

    boolean existsByNameAndBrandAndUser(String name, String brand, User User);
}
