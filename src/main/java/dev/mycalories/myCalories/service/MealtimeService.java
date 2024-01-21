package dev.mycalories.myCalories.service;

import dev.mycalories.myCalories.entity.Mealtime;

import java.util.Date;
import java.util.List;

public interface MealtimeService {
    List<Mealtime> findAllMealtimes();
    Mealtime findMealtimeById(long id);
    Mealtime findMealtimeByName(String name);
    Mealtime calcMealtimeByCurrentTime();

    String translateName(String mealtimeName);
}
