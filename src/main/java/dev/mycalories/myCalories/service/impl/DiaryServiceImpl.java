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
    public void addProduct(long id, int weight, Date date, Mealtime mealtime) {
        Product product = productsService.findProduct(id);
        Food food = foodService.findFood(product);
        Double weightValue = Double.parseDouble(String.valueOf(weight));
        Diary diary = new Diary(date, mealtime, food, weightValue);
        diaryRepository.save(diary);
    }

    @Override
    public Double calcDayKkal(Date currentDate) {
        List<Diary> dayEntries = findDayEntries(currentDate);
        double result = 0.0;
        for (Diary dayEntry : dayEntries) {
            Product product = dayEntry.getFood().getProduct();
            if (product != null) {
                EnergyValue energyValue = product.getEnergyValue();
                double kkal = energyValue.getKilocalorie() * dayEntry.getWeight() / 100;
                result = result + kkal;
            }
        }
        return result;
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
    public void editDiary(long id, Double weight) {
        Diary diary = findDiary(id);
        if (diary != null) {
            diary.setWeight(weight);
            diaryRepository.save(diary);
        }
    }

    private List<EntryView> createEntryViews(List<Diary> allByDate) {
        List<EntryView> result = new ArrayList<>();
        for (Diary diary : allByDate) {
            EntryView entryView = new EntryView();
            entryView.setId(diary.getId());
            entryView.setWeight(diary.getWeight());
            ProductView productView = productsService.createProductView(diary.getFood().getProduct());
            entryView.setProductView(productView);
            result.add(entryView);
        }
        return result;
    }

    private List<Diary> findDayEntries(Date currentDate) {
        return diaryRepository.findAllByDate(currentDate);
    }
}
