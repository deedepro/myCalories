package dev.mycalories.myCalories.service.impl;

import dev.mycalories.myCalories.entity.Users;
import dev.mycalories.myCalories.repository.UsersRepository;
import dev.mycalories.myCalories.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class RegistrationServiceImpl implements RegistrationService { //TODO: переименовать в UserService
    @Autowired
    private UserDetailsManager userDetailsManager;

    @Autowired
    private UsersRepository usersRepository;

    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder
            .getContextHolderStrategy();

    @Override
    public String createUser(@RequestParam() String username, String password) {
        if (usersRepository.existsByUsername(username)) {
            return "user with current username exist";
        } else {
            UserDetails user = User.withDefaultPasswordEncoder()
                    .username(username)
                    .password(password)
                    .authorities("USER")
                    .build();
            userDetailsManager.createUser(user);
            return null;
        }
    }

    @Override
    public Users getCurrentUser() {
        Authentication currentUser = this.securityContextHolderStrategy.getContext().getAuthentication();
        if (currentUser != null) {
            String username = currentUser.getName();
            return usersRepository.findUsersByUsername(username);
        } else {
            return null;
        }
    }


}
