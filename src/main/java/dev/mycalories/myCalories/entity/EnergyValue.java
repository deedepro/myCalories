package dev.mycalories.myCalories.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

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
    private BigDecimal protein;
    @NonNull
    private BigDecimal fat;
    @NonNull
    private BigDecimal carbohydrates;
    @NonNull
    private BigDecimal alimentaryFiber;
    @NonNull
    private BigDecimal kilocalorie;
}
