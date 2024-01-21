package dev.mycalories.myCalories.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Product {
    @Id
    @GeneratedValue(generator="my_seq")
    @SequenceGenerator(name="my_seq",sequenceName="MY_SEQ", allocationSize = 1)
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String brand;
    @NonNull
    @ManyToOne
    private User user;
    @NonNull
    @OneToOne(cascade = CascadeType.ALL)
    private EnergyValue energyValue;
}
