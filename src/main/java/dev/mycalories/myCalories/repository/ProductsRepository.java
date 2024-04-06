package dev.mycalories.myCalories.repository;

import dev.mycalories.myCalories.entity.Product;
import dev.mycalories.myCalories.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Long> {
    Iterable<Product> findAllByUser(User user);
    boolean existsByNameAndBrandAndUser(String name, String brand, User User);
}
