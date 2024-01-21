package dev.mycalories.myCalories.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductView {
    @NonNull
    private Long id;
    private String name, brand;
    private BigDecimal protein, fat, carb, fibers, kcal;

    private boolean userProduct;

    public ProductView(@NonNull Long id, String name, String brand) {
        this.id = id;
        this.name = name;
        this.brand = brand;
    }

    public void setEnergyValues(BigDecimal protein, BigDecimal fat, BigDecimal carb, BigDecimal fibers, BigDecimal kcal) {
        this.protein = protein;
        this.fat = fat;
        this.carb = carb;
        this.fibers = fibers;
        this.kcal = kcal;
    }
}
