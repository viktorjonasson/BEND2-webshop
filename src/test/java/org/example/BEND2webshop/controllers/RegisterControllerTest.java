package org.example.BEND2webshop.controllers;

import org.example.BEND2webshop.models.AppUser;
import org.example.BEND2webshop.models.UserRole;
import org.example.BEND2webshop.repositories.UserRepository;
import org.example.BEND2webshop.security.ConcreteUserDetails;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class  RegisterControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;


    private MultiValueMap<String, String> validCustomerForm, invalidForm;
    private ConcreteUserDetails admin;


    @BeforeEach
    void setUp() {
        validCustomerForm = getTestFormFields("test", "customer");
        invalidForm = getTestFormFields("", "");
        admin = getTestUser("admin");
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void registerShouldShowErrorForInvalidForm() {
        try {
            mockMvc.perform(post("/register")
                            .with(csrf())
                            .formFields(invalidForm))
                    .andExpect(status().is4xxClientError())
                    .andExpect(content().string(containsString(RegisterController.ERROR)))
                    .andExpect(content().string(containsString(RegisterController.INVALID_USERNAME_OR_PASSWORD)));
        } catch (Exception e) {
            fail("Unexpected Exception", e);
        }
    }

    @Test
    void registerShouldReturnOkForValidForm() {
        try {
            mockMvc.perform(post("/register")
                            .formFields(validCustomerForm)
                            .with(csrf()))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(flash().attribute(RegisterController.FEEDBACK_TYPE, RegisterController.SUCCESS))
                    .andExpect(flash().attribute(RegisterController.FEEDBACK_CONTENT, RegisterController.ACCOUNT_CREATED));

        } catch (Exception e) {
            fail("Unexpected Exception", e);
        }
    }

    @Test
    void registerAdminShouldReturnForbiddenForNonAdminUser() {
        try {
            var customer = getTestUser("customer");
            mockMvc.perform(post("/register-admin")
                            .with(csrf())
                            .with(user(customer))
                            .formFields(validCustomerForm))
                    .andExpect(status().isForbidden());

        } catch (Exception e) {
            fail("Unexpected Exception", e);
        }
    }

    @Test
    void registerAdminShouldReturnOkForAdmin() {
        try {
            mockMvc.perform(post("/register-admin")
                            .with(csrf())
                            .with(user(admin))
                            .formFields(validCustomerForm))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(flash().attribute(RegisterController.FEEDBACK_TYPE, RegisterController.SUCCESS))
                    .andExpect(flash().attribute(RegisterController.FEEDBACK_CONTENT, RegisterController.ACCOUNT_CREATED));

        } catch (Exception e) {
            fail("Unexpected Exception", e);
        }
    }

    @Test
    void registerAdminShouldReturnErrorForInvalidForm() {
        try {
            mockMvc.perform(post("/register-admin")
                            .with(csrf())
                            .with(user(admin))
                            .formFields(invalidForm))
                    .andExpect(status().is4xxClientError())
                    .andExpect(content().string(containsString(RegisterController.ERROR)))
                    .andExpect(content().string(containsString(RegisterController.INVALID_USERNAME_OR_PASSWORD)));

        } catch (Exception e) {
            fail("Unexpected Exception", e);
        }
    }

    private MultiValueMap<String, String> getTestFormFields(String username, String role) {
        return MultiValueMap.fromSingleValue(
                Map.of("username", username,
                        "password", "password",
                        "role", role
                )
        );
    }

    private ConcreteUserDetails getTestUser(String role) {
        return new ConcreteUserDetails(
                AppUser.builder()
                        .id(UUID.randomUUID())
                        .roles(List.of(new UserRole(UUID.randomUUID(), role)))
                        .username("test")
                        .password("password")
                        .enabled(true)
                        .build()
        );
    }


}