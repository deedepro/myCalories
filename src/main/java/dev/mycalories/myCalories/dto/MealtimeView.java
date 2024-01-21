package dev.mycalories.myCalories.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealtimeView {
    private String localizationName;
    private String mealtimeName;
    private int kcal;
}
