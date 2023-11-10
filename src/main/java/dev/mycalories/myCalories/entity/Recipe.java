package dev.mycalories.myCalories.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import dev.mycalories.myCalories.entity.User;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    private User user;

    public Recipe(String name, User user) {
        this.name = name;
        this.user = user;
    }
}
