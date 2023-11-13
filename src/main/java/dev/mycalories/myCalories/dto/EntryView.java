package dev.mycalories.myCalories.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntryView extends ProductView{
    Double weight;
    ProductView productView;

}
