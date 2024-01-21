package dev.mycalories.myCalories.repository;

import dev.mycalories.myCalories.entity.Recipe;
import dev.mycalories.myCalories.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Recipe findTopByName(String name);

    Iterable<Recipe> findAllByUser(User user);
}
