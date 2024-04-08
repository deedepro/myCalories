package dev.mycalories.myCalories.repository;

import dev.mycalories.myCalories.entity.Portion;
import dev.mycalories.myCalories.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortionRepository extends JpaRepository<Portion, Long> {
    List<Portion> findAllByProduct(Product product);
}
