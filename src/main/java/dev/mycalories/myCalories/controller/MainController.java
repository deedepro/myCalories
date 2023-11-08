package dev.mycalories.myCalories.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    String viewCorePage(Model model){
        return "home";
    }

    @GetMapping("/home")
    String viewHomePage(Model model){
        return "home";
    }

    @GetMapping("/hello")
    String viewHelloPage(Model model){
        return "hello";
    }

    @GetMapping("/login")
    String viewLoginPage(Model model){
        return "login";
    }
}
