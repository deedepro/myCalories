package dev.mycalories.myCalories.service;


import dev.mycalories.myCalories.entity.Food;

public interface DiaryService {
    void write(Food food);

    void delete(Food food);
}
