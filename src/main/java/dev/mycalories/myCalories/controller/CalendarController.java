package dev.mycalories.myCalories.controller;

import dev.mycalories.myCalories.dto.CalendarDay;
import dev.mycalories.myCalories.entity.Diary;
import dev.mycalories.myCalories.service.DiaryService;
import dev.mycalories.myCalories.service.EnergyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class CalendarController {
    private final EnergyService energyService;
    private final DiaryService diaryService;

    @Autowired
    public CalendarController(EnergyService energyService, DiaryService diaryService) {
        this.energyService = energyService;
        this.diaryService = diaryService;
    }

    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");

    private String prepareCalendarPage(Model model, Calendar calendar) {
        int firstDayOfWeek = getFirstDayOfWeek(calendar);
        int daysOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        //Цикл заполняет 42 возможные ячейки календаря числами месяца
        Map<Integer, CalendarDay> calendarMap = new HashMap<>();
        for (int i = 1; i <= 42; i++) {
            if (i >= firstDayOfWeek && i < daysOfMonth + firstDayOfWeek) {
                int day = i + 1 - firstDayOfWeek;
                CalendarDay calendarDay = buildCaledarDay(calendar, day);
                calendarMap.put(i, calendarDay);
            } else {
                calendarMap.put(i, null);
            }
        }
        model.addAttribute("calendarMap", calendarMap);

        //Для выделения "сегодня"
        int calendarMonth = calendar.get(Calendar.MONTH);
        calendar.setTime(new Date());
        int currentMonth = calendar.get(Calendar.MONTH);
        if (currentMonth == calendarMonth) {
            int today = calendar.get(Calendar.DAY_OF_MONTH);
            model.addAttribute("today", today);
        }

        return "calendar";
    }

    private CalendarDay buildCaledarDay(Calendar calendar, int day) {
        CalendarDay calendarDay = new CalendarDay();
        calendarDay.setDay(day);

        calendar.set(Calendar.DAY_OF_MONTH, day);
        Date date = calendar.getTime();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        calendarDay.setDate(sqlDate);

        List<Diary> dayEntries = diaryService.findDayEntries(sqlDate);
        BigDecimal kcal = energyService.calcKilocalorie(dayEntries);
        calendarDay.setKcal(kcal);
        return calendarDay;
    }

    private int getFirstDayOfWeek(Calendar calendar) {
        int result = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (result == 0) result = 7;
        return result;
    }

    @GetMapping("/calendar")
    String showCalendarPage(Model model) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String currentMonth = formatter.format(calendar.getTime());
        model.addAttribute("inputMonth", currentMonth);
        return prepareCalendarPage(model, calendar);
    }

    @PostMapping("/calendar")
    String showCustomCalendarPage(Model model, @RequestParam(value = "date") String date) throws ParseException {
        model.addAttribute("inputMonth", date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(formatter.parse(date));
        return prepareCalendarPage(model, calendar);
    }
}
