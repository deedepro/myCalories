package dev.mycalories.myCalories.service.impl;

import dev.mycalories.myCalories.dto.EntryView;
import dev.mycalories.myCalories.dto.ProductView;
import dev.mycalories.myCalories.entity.*;
import dev.mycalories.myCalories.repository.DiaryRepository;
import dev.mycalories.myCalories.service.DiaryService;
import dev.mycalories.myCalories.service.FoodService;
import dev.mycalories.myCalories.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class DiaryServiceImpl implements DiaryService {
    @Autowired
    private DiaryRepository diaryRepository;
    @Autowired
    private ProductsService productsService;
    @Autowired
    private FoodService foodService;

    @Override
    public Diary findDiary(long id) {
        return diaryRepository.findById(id).orElse(null);
    }

    @Override
    public void write(Food food) {

    }

    public void deleteEntry(long id) {
        Diary diary = findDiary(id);
        if (diary != null) {
            diaryRepository.delete(diary);
        }
    }

    @Override
    public void addEntry(long id, int weight, Date date, Mealtime mealtime) {
        Product product = productsService.findProduct(id);
        Food food = foodService.findFood(product);
        Diary diary = new Diary(date, mealtime, food, weight);
        diaryRepository.save(diary);
    }

    @Override
    public Double calcDayKcal(Date currentDate) {
        List<Diary> dayEntries = findDayEntries(currentDate);
        double result = 0.0;
        for (Diary dayEntry : dayEntries) {
            Product product = dayEntry.getFood().getProduct();
            if (product != null) {
                EnergyValue energyValue = product.getEnergyValue();
                double kcal = energyValue.getKilocalorie() * dayEntry.getWeight() / 100;
                result = result + kcal;
            }
        }
        return result;
    }

    @Override
    public EntryView makeEntryView(Long entryId) {
        Diary diary = findDiary(entryId);
        EntryView entryView = new EntryView();
        entryView.setId(diary.getId());
        entryView.setWeight(diary.getWeight());
        entryView.setMealtime(diary.getMealtime().getName());
        entryView.setDate(diary.getDate());
        ProductView productView = productsService.createProductView(diary.getFood().getProduct());
        entryView.setProductView(productView);
        return entryView;
    }

    @Override
    public List<EntryView> collectAllEntriesByDate(Date date) {
        List<Diary> allByDate = diaryRepository.findAllByDate(date);
        return createEntryViews(allByDate);
    }

    @Override
    public List<EntryView> collectAllEntriesByDateAndMealtime(Date date, Mealtime mealtime) {
        List<Diary> allByDateAndMealtime = diaryRepository.findAllByDateAndMealtime(date, mealtime);
        return createEntryViews(allByDateAndMealtime);
    }

    @Override
    public void editEntry(long inputId, int inputWeight, Date inputDate, Mealtime mealtime){
        Diary diary = findDiary(inputId);
        if (diary != null) {
            diary.setWeight(inputWeight);
            diary.setDate(inputDate);
            diary.setMealtime(mealtime);
            diaryRepository.save(diary);
        }
    }

    private List<EntryView> createEntryViews(List<Diary> allByDate) {
        List<EntryView> result = new ArrayList<>();
        for (Diary diary : allByDate) {
            EntryView entryView = makeEntryView(diary.getId());
            result.add(entryView);
        }
        return result;
    }

    private List<Diary> findDayEntries(Date currentDate) {
        return diaryRepository.findAllByDate(currentDate);
    }
}
