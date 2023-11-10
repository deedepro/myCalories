package dev.mycalories.myCalories.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "energy_values")
public class EnergyValue {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
//    @JoinColumn(name = "id")
    private Product product;

    private Double protein;
    private Double fat;
    private Double carbohydrates;
    private Double alimentaryFiber;
    private Double kilocalorie;

    public EnergyValue(Product product, Double protein, Double fat, Double carbohydrates, Double alimentaryFiber, Double kilocalorie) {
        this.product = product;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrates = carbohydrates;
        this.alimentaryFiber = alimentaryFiber;
        this.kilocalorie = kilocalorie;
    }
}
