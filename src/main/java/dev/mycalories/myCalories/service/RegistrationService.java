package dev.mycalories.myCalories.service;

import dev.mycalories.myCalories.entity.Users;

public interface RegistrationService {
    String createUser(String username, String password);

    Users getCurrentUser();
}
