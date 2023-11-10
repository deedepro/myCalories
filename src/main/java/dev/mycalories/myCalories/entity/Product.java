package dev.mycalories.myCalories.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Data
@Table(name = "products")
public class Product {
    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String brand;
    @ManyToOne
    private User user;

    public Product(String name, String brand, User user) {
        this.name = name;
        this.brand = brand;
        this.user = user;
    }
}
