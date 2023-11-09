package dev.mycalories.myCalories.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductView {
    @NonNull
    private Long id;
    private String name, brand;
    private String protein, fat, carb, fibers, kkal;

    public ProductView(@NonNull Long id, String name, String brand) {
        this.id = id;
        this.name = name;
        this.brand = brand;
    }

    public void setEnergyValues(Double protein, Double fat, Double carb, Double fibers, Double kkal) {
        this.protein = protein.toString();
        this.fat = fat.toString();
        this.carb = carb.toString();
        this.fibers = fibers.toString();
        this.kkal = kkal.toString();
    }
}
