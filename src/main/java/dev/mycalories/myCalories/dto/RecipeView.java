package dev.mycalories.myCalories.dto;

import lombok.*;

import java.math.BigInteger;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class RecipeView extends ProductView{
    private BigInteger weight;

    public RecipeView(@NonNull Long id, String name, BigInteger weight) {
        super(id, name, null);
        this.weight = weight;
    }
}
