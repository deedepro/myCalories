package dev.mycalories.myCalories.controller;

import dev.mycalories.myCalories.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/")
    String showCorePage(Model model) {
        return "home";
    }

    @GetMapping("/home")
    String showHomePage(Model model) {
        return "home";
    }

    @GetMapping("/hello")
    String showHelloPage(Model model) {
        return "hello";
    }

    @GetMapping("/login")
    String showLoginPage(Model model) {
        return "login";
    }

    @GetMapping("/registration")
    String showRegistrationPage(Model model) {
        return "registration";
    }

    @PostMapping("/registration")
    String register(@RequestParam String username,
                    @RequestParam String password,
                    Model model) {
        String errorMessage = registrationService.createUser(username, password);
        String validMessage = "Тест успешно пройден";
        String message = errorMessage != null ? errorMessage : validMessage;
        model.addAttribute("message", message);
        return null;
    }
}
