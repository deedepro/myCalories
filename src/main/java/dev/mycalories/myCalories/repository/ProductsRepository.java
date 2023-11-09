package dev.mycalories.myCalories.repository;

import dev.mycalories.myCalories.entity.Products;
import dev.mycalories.myCalories.entity.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends CrudRepository<Products, Long> {
    Products findTopByOrderByIdDesc();

    boolean existsByNameAndBrandAndUser(String name, String brand, Users User);
}
