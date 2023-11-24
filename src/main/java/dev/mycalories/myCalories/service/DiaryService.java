package dev.mycalories.myCalories.service;


import dev.mycalories.myCalories.dto.EntryView;
import dev.mycalories.myCalories.entity.Diary;
import dev.mycalories.myCalories.entity.Food;
import dev.mycalories.myCalories.entity.Mealtime;

import java.sql.Date;
import java.util.List;

public interface DiaryService {

    Diary findDiary(long id);

    void write(Food food);

    void deleteEntry(long id);

    void addProduct(long id, int weight, Date date, Mealtime mealtime);

    Double calcDayKkal(Date currentDate);

    List<EntryView> collectAllEntriesByDate(Date currentDate);

    List<EntryView> collectAllEntriesByDateAndMealtime(Date currentDate, Mealtime mealtime);

    void editDiary(long id, Double weight);
}
