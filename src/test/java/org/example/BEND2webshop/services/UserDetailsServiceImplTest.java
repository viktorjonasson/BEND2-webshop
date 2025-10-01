package org.example.BEND2webshop.services;

import org.example.BEND2webshop.config.UserDataSeeder;
import org.example.BEND2webshop.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class UserDetailsServiceImplTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired UserDetailsServiceImpl userDetailsService;

    private String username, notExistingUsername, password;
    private Set<String> roles;

    @BeforeEach
    void setUp() {
        username = "test-user";
        password = "test-password";
        roles = Set.of("admin");
        notExistingUsername = "no-user";
        userService.saveUser(username, roles, password);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void shouldThrowExceptionWhenUserNotExists() {
        assertNull(userRepository.findByUsernameIgnoreCase(notExistingUsername));
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername(notExistingUsername);
        });
    }

    @Test
    void shouldLoadUser() {
        var userDetails = userDetailsService.loadUserByUsername(username);
        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
    }
}