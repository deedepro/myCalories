package dev.mycalories.myCalories.repository;

import dev.mycalories.myCalories.entity.Portion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortionRepository extends JpaRepository<Portion, Long> {

}
