package dev.mycalories.myCalories.service;


import dev.mycalories.myCalories.dto.EntityView;
import dev.mycalories.myCalories.entity.Diary;
import dev.mycalories.myCalories.entity.Food;
import dev.mycalories.myCalories.entity.Mealtime;

import java.math.BigInteger;
import java.sql.Date;
import java.util.List;

public interface DiaryService {

    Diary findDiary(long id);

    void write(Food food);

    void deleteEntity(long id);

    void addEntity(long productId, BigInteger count, Date date, Mealtime mealtime);
    EntityView createEntityView(Diary diary);
    EntityView createTotalEntityView(List<EntityView> entities, String mealtimeName);

    List<EntityView> collectAllEntitiesByDate(Date currentDate);

    List<EntityView> collectAllEntitiesByDateAndMealtime(Date currentDate, Mealtime mealtime);

    void editEntry(long inputId, BigInteger inputWeight, Date inputDate, Mealtime mealtime);

    List<Diary> findDayEntries(Date date);
    List<Diary> findMealtimeEntries(Date date, Mealtime mealtime);
}
