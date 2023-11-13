package dev.mycalories.myCalories.service;

import dev.mycalories.myCalories.entity.User;

public interface UserService {
    String createUser(String username, String password);

    User getCurrentUser();

    boolean isAuthentication();
}
