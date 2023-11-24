package dev.mycalories.myCalories.service;

import dev.mycalories.myCalories.entity.Mealtime;

import java.util.Date;

public interface MealtimeService {
    Mealtime findMealtimeByName(String name);

    Mealtime calcMealtimeByCurrentTime();
}
