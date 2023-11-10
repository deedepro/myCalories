package dev.mycalories.myCalories.service;

import dev.mycalories.myCalories.entity.User;

public interface RegistrationService {
    String createUser(String username, String password);

    User getCurrentUser();
}
