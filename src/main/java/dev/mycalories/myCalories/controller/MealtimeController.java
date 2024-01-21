package dev.mycalories.myCalories.controller;

import dev.mycalories.myCalories.entity.Diary;
import dev.mycalories.myCalories.entity.Mealtime;
import dev.mycalories.myCalories.service.DiaryService;
import dev.mycalories.myCalories.service.EnergyService;
import dev.mycalories.myCalories.service.MealtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MealtimeController {
    private final EnergyService energyService;

    private final MealtimeService mealtimeService;
    private final DiaryService diaryService;

    @Autowired
    public MealtimeController(EnergyService energyService, MealtimeService mealtimeService, DiaryService diaryService) {
        this.energyService = energyService;
        this.mealtimeService = mealtimeService;
        this.diaryService = diaryService;
    }

    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");

    String preparePage(Model model, Date date) {
        //FIXME игнорируются кастомные приемы пищи
        Map<String, BigDecimal> mealtimeToKcal = new HashMap<>();
        List<Mealtime> mealtimes = mealtimeService.findAllMealtimes();
        for (Mealtime mealtime : mealtimes) {
            List<Diary> mealtimeEntries = diaryService.findMealtimeEntries(date, mealtime);
            BigDecimal mealtimeKcal = energyService.calcKilocalorie(mealtimeEntries);
            mealtimeToKcal.put(mealtime.getName(), mealtimeKcal);
        }
        List<Diary> dayEntries = diaryService.findDayEntries(date);
        BigDecimal dayKcal = energyService.calcKilocalorie(dayEntries);
        mealtimeToKcal.put("all", dayKcal);
        model.addAttribute("map", mealtimeToKcal);
        model.addAttribute("inputDate", date);

        model.addAttribute("backDate", formatter.format(date));

        return "mealtimes";
    }

    @GetMapping("/mealtimes")
    String showCustomMealtimesPage(Model model, @RequestParam(value = "date", required = false) Date date) {
        if (date == null) {
            date = new Date(System.currentTimeMillis());
        }
        return preparePage(model, date);
    }

}
