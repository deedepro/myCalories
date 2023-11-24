package dev.mycalories.myCalories.controller;

import dev.mycalories.myCalories.dto.EntryView;
import dev.mycalories.myCalories.dto.ProductView;
import dev.mycalories.myCalories.entity.Mealtime;
import dev.mycalories.myCalories.service.DiaryService;
import dev.mycalories.myCalories.service.MealtimeService;
import dev.mycalories.myCalories.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Класс отвечает за работу сайта по пути /diary
 */
@Controller
public class DiaryController {
    @Autowired
    public DiaryController(DiaryService diaryService, MealtimeService mealtimeService, ProductsService productsService) {
        this.diaryService = diaryService;
        this.mealtimeService = mealtimeService;
        this.productsService = productsService;
    }

    /**
     * Сервис для работы с моделью дневника
     */
    private final DiaryService diaryService;

    /**
     * Сервис для работы с моделью приема пищи
     */
    private final MealtimeService mealtimeService;

    /**
     * TODO: попробовать избавиться от него
     * Сервис для работы с моделью продукта
     */
    private final ProductsService productsService;

    /**
     * Подготовка параметров страницы дневника
     * @param mealtime модель приема пищи
     * @param date дата
     * @param model параметры страницы
     * @return ссылка на html шаблон страницы дневника
     */
    private String prepareDiaryPage(Mealtime mealtime, Date date, Model model) {
        if (date == null) {
            date = new Date(System.currentTimeMillis());
        }
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        String dateValue = df.format(date);
        model.addAttribute("date", dateValue);

        if (mealtime == null) {
            model.addAttribute("mealtime", "all");
        } else {
            model.addAttribute("mealtime", mealtime.getName());
        }

        Double kkal = diaryService.calcDayKkal(date);
        model.addAttribute("kkal", kkal);

        List<ProductView> allProducts = productsService.collectAllProducts();
        model.addAttribute("products", allProducts);

        List<EntryView> entries;
        if (mealtime != null) {
            entries = diaryService.collectAllEntriesByDateAndMealtime(date, mealtime);
        } else {
            entries = diaryService.collectAllEntriesByDate(date);
        }
        model.addAttribute("entries", entries);

        return "diary/diary";
    }

    /**
     * Показать страницу дневника с параметрами, определенными системой
     * @param model параметры страницы
     * @return обновление страницы дневника
     */
    @GetMapping("/diary")
    String showStartDiaryPage(Model model) {
        Mealtime mealtime = mealtimeService.calcMealtimeByCurrentTime();
        Date date = new Date(System.currentTimeMillis());
        return prepareDiaryPage(mealtime, date, model);
    }

    /**
     * Показать страницу дневника с выбранными пользователем параметрами
     * @param dateValue значение выбранной пользователем даты в формате строки
     * @param mealtimeValue наименование выбранного пользователем приема пищи
     * @param model параметры страницы
     * @return обновление страницы дневника
     */
    @PostMapping("/diary")
    public String showDiaryPage(@RequestParam(value = "select-date") String dateValue,
                                @RequestParam(value = "select-mealtime") String mealtimeValue,
                                Model model) {
        Mealtime mealtime = null;
        if (mealtimeValue != null) {
            mealtime = mealtimeService.findMealtimeByName(mealtimeValue);
        }

        Date date = mapDate(dateValue);

        return prepareDiaryPage(mealtime, date, model);
    }

    /**
     * //TODO попробовать избавится
     * Конвертер даты из параметра строки в параметр даты
     * @param dateValue значение даты в формате строки
     * @return значение даты в формате даты
     */
    private Date mapDate(String dateValue) {
        Date date = null;
        if (!dateValue.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate localDate = LocalDate.parse(dateValue, formatter);
            date = Date.valueOf(localDate);
        }
        return date;
    }

    /**
     * Добавить запись в дневник
     * @param id идентификатор выбранного продукта
     * @param mealtimeParam наименование выбранного пользователем приема пищи
     * @param dateParam выбранная пользователем дата в формате строки
     * @param weight указанный пользователем вес
     * @return обновление страницы дневника
     */
    @PostMapping("/diary/add/{id}")
    String addFoodToDiary(@PathVariable(value = "id") long id,
                          @RequestParam(value = "mealtime") String mealtimeParam,
                          @RequestParam(value = "date") String dateParam,
                          @RequestParam(value = "weight") int weight,
                          Model model) {
        Mealtime mealtime = mealtimeService.findMealtimeByName(mealtimeParam);
        Date date = mapDate(dateParam);
        diaryService.addProduct(id, weight, date, mealtime);
        String resultMessage = "Успешно добавлено";
        model.addAttribute("message", resultMessage);
        return "redirect:/diary";
    }

    /**
     * Изменить запись в дневнике
     * @param id идентификатор изменяемой записи
     * @param weight текущий вес в записи
     * @param model параметры страницы
     * @return обновление страницы дневника
     */
    @PostMapping("/diary/edit/{id}")
    String editDiaryEntry(@PathVariable(value = "id") long id,
                          @RequestParam(value = "weight") double weight,
                          Model model) {
        diaryService.editDiary(id, weight);
        return "redirect:/diary";
    }

    /**
     * Удалить запись из дневника
     * @param id идентификатор удаляемой записи
     * @param model параметры страницы
     * @return обновление страницы дневника
     */
    @PostMapping("/diary/del/{id}")
    String deleteEntry(@PathVariable(value = "id") long id,
                            Model model) {
        diaryService.deleteEntry(id);
        return "redirect:/diary";
    }
}
