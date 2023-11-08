package dev.mycalories.myCalories.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class EnergyValues {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
//    @JoinColumn(name = "id")
    private Products product;

    private Double protein;
    private Double fat;
    private Double carbohydrates;
    private Double kilocalorie;
    private Double alimentaryFiber;
}
