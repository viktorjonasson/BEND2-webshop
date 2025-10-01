package org.example.BEND2webshop.controllers;

import org.example.BEND2webshop.config.UserDataSeeder;
import org.example.BEND2webshop.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RegisterControllerTest {

    @Autowired
    MockMvc mockMvc;

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
    void registerAdminShouldReturnErrorForAnonymous() {}

    @Test
    void registerAdminShouldReturnErrorForNonAdminUser() {}

    @Test
    void registerAdminShouldReturnOkForAdmin() {}

    @Test
    void registerShouldReturnErrorForInvalidForm() {}

    @Test
    void registerShouldReturnOkForValidForm() {}

    @Test
    void registerAdminShouldReturnErrorForInvalidForm() {}

    @Test
    void registerAdminShouldReturnOkForValidForm() {}


}