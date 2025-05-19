package com.example.recipeapi.admin;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.List;

@Configuration
public class TestUserInitializer {

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        var user1 = User.withUsername("testuser")
                .password("{noop}password") // {noop} = no encoding
                .roles("USER")
                .build();

        var user2 = User.withUsername("admin")
                .password("{noop}admin123")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user1, user2);
    }

    @Bean
    public CommandLineRunner printUsers(UserDetailsService userDetailsService) {
        return args -> {
            if (userDetailsService instanceof InMemoryUserDetailsManager manager) {
                System.out.println("==== Authorized In-Memory Users ====");
                for (var username : List.of("testuser", "admin")) {
                    var user = manager.loadUserByUsername(username);
                    System.out.printf("Username: %s | Password: [see code] | Roles: %s%n",
                            user.getUsername(),
                            user.getAuthorities());
                }
            }
        };
    }
}