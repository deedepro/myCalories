package dev.mycalories.myCalories.controller;

import dev.mycalories.myCalories.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    String showCorePage(Model model) {
        if (userService.isAuthentication()){
            return "home";
        }
        else {
            return "hello";
        }
    }

    @GetMapping("/hello")
    String showHelloPage(Model model) {
        return "hello";
    }

    @GetMapping("/login")
    String showLoginPage(Model model) {
        return "login";
    }

    @GetMapping("/reg")
    String showRegistrationPage(Model model) {
        return "reg";
    }

    @GetMapping("/settings")
    String showSettingsPage(Model model) {
        return "settings";
    }

    @GetMapping("/settings/pass")
    String showChangePasswordPage(Model model) {
        return "pass";
    }

    @PostMapping("/registration")
    String register(@RequestParam String username,
                    @RequestParam String password,
                    Model model) {
        String errorMessage = userService.createUser(username, password);
        String validMessage = "Регистрация завершена";
        String message = errorMessage != null ? errorMessage : validMessage;
        model.addAttribute("message", message);
        return null;
    }
}
