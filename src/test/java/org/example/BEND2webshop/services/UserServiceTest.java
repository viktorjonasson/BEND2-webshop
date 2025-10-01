package org.example.BEND2webshop.services;

import org.example.BEND2webshop.config.UserDataSeeder;
import org.example.BEND2webshop.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class UserServiceTest {
    @Autowired
    UserDataSeeder userDataSeeder;

    @Autowired
    UserRepository userRepository;


    @BeforeEach
    void setUp() {
        userDataSeeder.Seed();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void saveUserShouldReturnErrorWhenUserExists() {

    }

    @Test
    void shouldSaveUserWhenValid() {}
}