package dev.mycalories.myCalories.repository;

import dev.mycalories.myCalories.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
}
