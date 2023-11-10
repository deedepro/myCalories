package dev.mycalories.myCalories.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ingredients")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Рецепт
     */
    @ManyToOne
    private Recipe recipe;
    @ManyToOne
    private Product product;
    private Double weight;

    public Ingredient(Recipe recipe, Product product, Double weight) {
        this.recipe = recipe;
        this.product = product;
        this.weight = weight;
    }
}
