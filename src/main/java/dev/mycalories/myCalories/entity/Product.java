package dev.mycalories.myCalories.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    private EnergyValue energyValue;

    public Product(String name, String brand, User user) {
        this.name = name;
        this.brand = brand;
        this.user = user;
    }
}
