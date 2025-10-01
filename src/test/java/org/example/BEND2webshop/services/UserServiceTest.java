package org.example.BEND2webshop.services;

import org.example.BEND2webshop.exceptions.UsernameNotAvailableException;
import org.example.BEND2webshop.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class UserServiceTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    private String username, password;
    private Set<String> roles;


    @BeforeEach
    void setUp() {
        username = "test-user";
        password = "test-password";
        roles = Set.of("admin");
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void saveUserShouldThrowWhenUserExists() {
        assertNull(userRepository.findByUsernameIgnoreCase(username));

        userService.saveUser(username, roles, password);

        assertThrows(UsernameNotAvailableException.class, () -> userService.saveUser(username, roles, password));
    }

    @Test
    void shouldSaveUserWhenValid() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        assertNull(userRepository.findByUsernameIgnoreCase(username));

        userService.saveUser(username, roles, password);

        var test = userRepository.findByUsernameIgnoreCase(username);

        assertNotNull(test);
        assertEquals(username, test.getUsername());
        assertTrue(encoder.matches(password, test.getPassword()));

    }
}