package dev.mycalories.myCalories.controller;

import dev.mycalories.myCalories.dto.EntryView;
import dev.mycalories.myCalories.dto.ProductView;
import dev.mycalories.myCalories.entity.Diary;
import dev.mycalories.myCalories.service.DiaryService;
import dev.mycalories.myCalories.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@Controller
public class DiaryController {

    @Autowired
    private DiaryService diaryService;


    @Autowired
    private ProductsService productsService;

    @GetMapping("/diary")
    String showDiaryPage(Model model){
        Date currentDate = new Date(System.currentTimeMillis());
        Double kkal = diaryService.calcDayKkal(currentDate);
        model.addAttribute("kkal", kkal);

        List<ProductView> allProducts = productsService.collectAllProducts();
        model.addAttribute("products", allProducts);

        List<EntryView> allEntries = diaryService.collectAllEntries(currentDate);
        model.addAttribute("entries", allEntries);
        return "diary/diary";
    }

    @RequestMapping(value = "diary/testMethod", method = RequestMethod.GET)
    public @ResponseBody Responce testMethod(@RequestParam(value = "weight") int weight,
                                             @RequestParam(value = "id") String idParam){
        long id = Long.parseLong(idParam);
        diaryService.addProduct(id, weight);
        Responce responce = new Responce();
        responce.setText("😄");
        return responce;
    }

    @PostMapping("/diary/add/{id}")
    String addFoodToDiary(@PathVariable(value = "id") long id,
                          @RequestParam(value = "weight") int weight,
                          Model model){
        diaryService.addProduct(id, weight);
        String resultMessage = "Успешно добавлено";
        model.addAttribute("message", resultMessage);
        return "redirect:/products/all";
    }

    @PostMapping("/diary/edit/{id}")
    String editDiaryEntry(@PathVariable(value = "id") long id,
                          @RequestParam(value = "weight") double weight,
                          Model model){
        Diary diary = diaryService.findDiary(id);
        diary.setWeight(weight);
        diaryService.editDiary(diary);
        return "redirect:/diary";
    }

    @PostMapping("/diary/del/{id}")
    String deleteDiaryEntry(@PathVariable(value = "id") long id,
                         Model model){
        Diary diary = diaryService.findDiary(id);
        diaryService.delete(diary);
        return "redirect:/diary";
    }

    static class Responce{
        String text;
        String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Responce() {
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
