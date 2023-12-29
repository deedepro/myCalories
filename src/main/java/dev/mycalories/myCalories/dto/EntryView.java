package dev.mycalories.myCalories.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntryView extends ProductView {
    Long id;
    Date date;
    int weight;
    String mealtime;
    ProductView productView;
}
