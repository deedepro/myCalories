package dev.mycalories.myCalories.controller;

import dev.mycalories.myCalories.entity.User;
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
        if (userService.isAuthentication()) {
            return "home";
        } else {
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
        User currentUser = userService.getCurrentUser();
        model.addAttribute("user", currentUser);
        return "settings";
    }

    @PostMapping("settings/save")
    String changeSettings(Model model,
                          @RequestParam(value = "username") String username,
                          @RequestParam(value = "email") String email) {
        if (userService.isUsernameExist(username)) {
            model.addAttribute("error", "error");
        } else {
            userService.getCurrentUser().setUsername(username);
        }
        return "redirect:/settings";
    }

    @GetMapping("/settings/pass")
    String showChangePasswordPage(Model model) {
        return "pass";
    }


    //TODO: Добавить email
    @PostMapping("/reg")
    String register(@RequestParam String username,
                    @RequestParam String password,
                    @RequestParam String passwordCheck,
                    Model model) {
        String errorMessage;
        if (password.equals(passwordCheck)) {
            errorMessage = userService.createUser(username, password);
        } else {
            errorMessage = "Введенные пароли не совпадают";
        }
        if (errorMessage != null) {
            model.addAttribute("message", errorMessage);
            return null;
        } else {
            return "redirect:/login";
        }
    }
}
