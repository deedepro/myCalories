package dev.mycalories.myCalories.service.impl;

import dev.mycalories.myCalories.entity.Mealtime;
import dev.mycalories.myCalories.repository.MealtimesRepository;
import dev.mycalories.myCalories.service.MealtimeService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MealtimeServiceImpl implements MealtimeService {

    @Autowired
    private MealtimesRepository mealtimesRepository;

    @Override
    public Mealtime findMealtimeByName(String name) {
        return mealtimesRepository.findTopByName(name);
    }

    @Override
    public Mealtime calcMealtimeByCurrentTime() {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime startTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(),
                currentTime.getDayOfMonth(), 0, 0);
        LocalDateTime breakfastEndTime = startTime.plusHours(10);
        LocalDateTime lunchEndTime = startTime.plusHours(17);
        String mealtimeName;
        if (currentTime.isBefore(breakfastEndTime)) {
            mealtimeName = "breakfast";
        } else if (currentTime.isBefore(lunchEndTime)) {
            mealtimeName = "lunch";
        } else {
            mealtimeName = "dinner";
        }
        return mealtimesRepository.findTopByName(mealtimeName);
    }

    @PostConstruct
    private void createStartMealtimes() {
        List<Mealtime> all = mealtimesRepository.findAll();
        if (all.isEmpty()) {
            Mealtime breakfast = new Mealtime("breakfast");
            Mealtime lunch = new Mealtime("lunch");
            Mealtime dinner = new Mealtime("dinner");
            Mealtime snack = new Mealtime("snack");
            List<Mealtime> mealtimes = List.of(breakfast, lunch, dinner, snack);
            mealtimesRepository.saveAll(mealtimes);
        }
    }
}
