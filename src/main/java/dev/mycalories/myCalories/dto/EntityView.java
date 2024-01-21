package dev.mycalories.myCalories.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityView{
    Long id;
    Date date;
    BigInteger weight;
    String mealtime;
    ProductView productView;
}
