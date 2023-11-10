package dev.mycalories.myCalories.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Recipe recipe;

    @OneToOne(cascade = CascadeType.ALL)
    private Product product;

    public Food(Product product) {
        this.product = product;
    }

    public Food(Recipe recipe) {
        this.recipe = recipe;
    }
}
