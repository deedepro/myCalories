package dev.mycalories.myCalories.repository;

import dev.mycalories.myCalories.entity.Diary;
import dev.mycalories.myCalories.entity.Mealtime;
import dev.mycalories.myCalories.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findAllByUserAndDate(User user, Date date);
    List<Diary> findAllByUserAndDateAndMealtime(User user, Date date, Mealtime mealtime);
}
