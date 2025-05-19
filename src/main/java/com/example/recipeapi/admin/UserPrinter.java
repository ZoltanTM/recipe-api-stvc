package com.example.recipeapi.admin;

import com.example.recipeapi.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserPrinter implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) {
        System.out.println("==== Authorized Users from DB ====");
        userRepository.findAll().forEach(user -> {
            System.out.printf("Username: %s | Hashed Password: %s | Roles: %s%n",
                    user.getName(),
                    user.getPassword(),
                    user.getRole()); // or whatever your field is
        });
    }
}