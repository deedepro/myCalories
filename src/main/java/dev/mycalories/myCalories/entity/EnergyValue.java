package dev.mycalories.myCalories.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "energy_values")
public class EnergyValue {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private Double protein;
    private Double fat;
    private Double carbohydrates;
    private Double alimentaryFiber;
    private Double kilocalorie;

    public EnergyValue(Double protein, Double fat, Double carbohydrates, Double alimentaryFiber, Double kilocalorie) {
        this.protein = protein;
        this.fat = fat;
        this.carbohydrates = carbohydrates;
        this.alimentaryFiber = alimentaryFiber;
        this.kilocalorie = kilocalorie;
    }
}
