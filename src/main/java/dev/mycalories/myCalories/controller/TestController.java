package dev.mycalories.myCalories.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;

@Controller
public class TestController {

    @GetMapping("/test")
    String showTestPage(Model model) {
        return prepareTestPage(model, null, null);
    }

    @GetMapping("/test/params")
    String showTestParamsPage(Model model,
                              @RequestParam(value = "test_date") Date inputDate,
                              @RequestParam(value = "test_select") String inputSelect) {
        return prepareTestPage(model, inputDate, inputSelect);
    }

    String prepareTestPage(Model model, Date date, String selectValue) {
        if (date == null) {
            date = new Date(System.currentTimeMillis());
        }
        model.addAttribute("dateAttr", date);

        if (selectValue == null) {
            selectValue = "1";
        }
        model.addAttribute("selectAttr", selectValue);

        return "test";
    }
}
