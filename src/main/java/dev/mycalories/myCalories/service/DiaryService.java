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

    void addEntry(long id, int weight, Date date, Mealtime mealtime);

    Double calcDayKcal(Date currentDate);
    EntryView makeEntryView(Long productId);

    List<EntryView> collectAllEntriesByDate(Date currentDate);

    List<EntryView> collectAllEntriesByDateAndMealtime(Date currentDate, Mealtime mealtime);

    void editEntry(long inputId, int inputWeight, Date inputDate, Mealtime mealtime);
}
