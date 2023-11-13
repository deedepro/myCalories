package dev.mycalories.myCalories.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "energy_values")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class EnergyValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private Double protein;
    @NonNull
    private Double fat;
    @NonNull
    private Double carbohydrates;
    @NonNull
    private Double alimentaryFiber;
    @NonNull
    private Double kilocalorie;
}
