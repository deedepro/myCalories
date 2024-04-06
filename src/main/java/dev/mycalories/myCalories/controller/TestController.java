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
        return null;
    }
}
