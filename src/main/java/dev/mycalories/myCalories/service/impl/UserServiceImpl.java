package dev.mycalories.myCalories.service.impl;

import dev.mycalories.myCalories.entity.User;
import dev.mycalories.myCalories.repository.UsersRepository;
import dev.mycalories.myCalories.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class UserServiceImpl implements UserService {
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
            UserDetails user = org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder()
                    .username(username)
                    .password(password)
                    .authorities("USER")
                    .build();
            userDetailsManager.createUser(user);
            return null;
        }
    }

    @Override
    public User getCurrentUser() {
        Authentication currentUser = this.securityContextHolderStrategy.getContext().getAuthentication();
        if (currentUser != null) {
            String username = currentUser.getName();
            return usersRepository.findUsersByUsername(username);
        } else {
            return null;
        }
    }

    @Override
    public boolean isAuthentication() {
        Authentication authentication = this.securityContextHolderStrategy.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.isAuthenticated();
        } else {
            return false;
        }
    }


}
