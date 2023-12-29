package dev.mycalories.myCalories.controller;

import dev.mycalories.myCalories.dto.EntryView;
import dev.mycalories.myCalories.entity.Mealtime;
import dev.mycalories.myCalories.service.DiaryService;
import dev.mycalories.myCalories.service.MealtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.List;

/**
 * Класс отвечает за работу сайта по пути /diary
 */
@Controller
public class DiaryController {
    @Autowired
    public DiaryController(DiaryService diaryService, MealtimeService mealtimeService) {
        this.diaryService = diaryService;
        this.mealtimeService = mealtimeService;
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
     * Подготовка параметров страницы дневника
     *
     * @param mealtime модель приема пищи
     * @param date     дата
     * @param model    параметры страницы
     * @return ссылка на html шаблон страницы дневника
     */
    private String prepareDiaryPage(Mealtime mealtime, Date date, Model model) {
        if (date == null) {
            date = new Date(System.currentTimeMillis());
        }
        model.addAttribute("dateAttr", date);

        List<EntryView> entries;
        if (mealtime == null) {
            model.addAttribute("mealtimeAttr", "all");
            entries = diaryService.collectAllEntriesByDate(date);
        } else {
            model.addAttribute("mealtimeAttr", mealtime.getName());
            entries = diaryService.collectAllEntriesByDateAndMealtime(date, mealtime);
        }
        model.addAttribute("entries", entries);

        return "diary/diary";
    }

    /**
     * Показать страницу дневника с параметрами, определенными системой
     *
     * @param model параметры страницы
     * @return обновление страницы дневника
     */
    @GetMapping("/diary")
    String showDiaryPage(Model model) {
        Mealtime mealtime = mealtimeService.calcMealtimeByCurrentTime();
        return prepareDiaryPage(mealtime, null, model);
    }

    /**
     * Показать страницу дневника с выбранными пользователем параметрами
     *
     * @param inputDate     значение выбранной пользователем даты в формате строки
     * @param inputMealtime наименование выбранного пользователем приема пищи
     * @param model         параметры страницы
     * @return обновление страницы дневника
     */
    @PostMapping("/diary")
    public String showDiaryParamsPage(@RequestParam(value = "inputDate") Date inputDate,
                                @RequestParam(value = "inputMealtime") String inputMealtime,
                                Model model) {
        Mealtime mealtime = mealtimeService.findMealtimeByName(inputMealtime);
        return prepareDiaryPage(mealtime, inputDate, model);
    }

    /**
     * Добавить запись в дневник
     *
     * @param inputId       идентификатор выбранного продукта
     * @param inputMealtime наименование выбранного пользователем приема пищи
     * @param inputDate     выбранная пользователем дата в формате строки
     * @param inputWeight   указанный пользователем вес
     * @param model         параметры страницы
     * @return обновление страницы дневника
     */
    @PostMapping("/diary/add")
    String addFoodToDiary(@RequestParam(value = "inputId") long inputId,
                          @RequestParam(value = "inputMealtime") String inputMealtime,
                          @RequestParam(value = "inputDate") Date inputDate,
                          @RequestParam(value = "inputWeight") int inputWeight,
                          Model model) {
        Mealtime mealtime = mealtimeService.findMealtimeByName(inputMealtime);
        diaryService.addEntry(inputId, inputWeight, inputDate, mealtime);
        return "redirect:/products";
    }

    /**
     * Обработчик события открытия страницы "Дневник" в режиме изменений записи дневника
     *
     * @param inputId       идентификатор выбранного продукта
     * @param model         параметры страницы
     * @return открытие страницы "Дневник"
     */
    @GetMapping("/diary/edit/{inputId}")
    String showEditPage(@PathVariable(value = "inputId") long inputId, Model model) {
        model.addAttribute("edit",true);
        EntryView editEntry = diaryService.makeEntryView(inputId);
        model.addAttribute("entry", editEntry);
        return showDiaryPage(model);
    }

    /**
     * Обработчик события изменения записи дневника в системе
     *
     * @param inputId       идентификатор выбранного продукта
     * @param inputMealtime наименование выбранного пользователем приема пищи
     * @param inputDate     выбранная пользователем дата в формате строки
     * @param inputWeight   указанный пользователем вес
     * @param model         параметры страницы
     * @return переадресация на страницу "Дневника"
     */
    @PostMapping("/diary/edit")
    String editEntry(@RequestParam(value = "inputId") long inputId,
                     @RequestParam(value = "inputMealtime") String inputMealtime,
                     @RequestParam(value = "inputDate") Date inputDate,
                     @RequestParam(value = "inputWeight") int inputWeight,
                     Model model) {
        Mealtime mealtime = mealtimeService.findMealtimeByName(inputMealtime);
        diaryService.editEntry(inputId, inputWeight, inputDate, mealtime);
        return "redirect:/diary";
    }

    /**
     * Удалить запись из дневника
     *
     * @param id    идентификатор удаляемой записи
     * @param model параметры страницы
     * @return обновление страницы дневника
     */
    @GetMapping("/diary/del/{inputId}")
    String deleteEntry(@PathVariable(value = "inputId") long id,
                       Model model) {
        diaryService.deleteEntry(id);
        return "redirect:/diary";
    }
}
