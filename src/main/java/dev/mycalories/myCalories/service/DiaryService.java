package dev.mycalories.myCalories.service;


import dev.mycalories.myCalories.dto.EntryView;
import dev.mycalories.myCalories.entity.Diary;
import dev.mycalories.myCalories.entity.Food;

import java.sql.Date;
import java.util.List;

public interface DiaryService {

    Diary findDiary(long id);
    void write(Food food);

    void delete(Diary diary);

    void addProduct(long id, int weight);

    Double calcDayKkal(Date currentDate);

    List<EntryView> collectAllEntries(Date currentDate);

    void editDiary(Diary diary);
}
