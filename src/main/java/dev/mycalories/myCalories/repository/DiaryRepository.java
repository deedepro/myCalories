package dev.mycalories.myCalories.repository;

import dev.mycalories.myCalories.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findAllByDate(Date date);
}
