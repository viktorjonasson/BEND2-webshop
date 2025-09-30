package org.example.BEND2webshop.controllers;

import org.example.BEND2webshop.models.AppUser;
import org.example.BEND2webshop.models.UserRole;
import org.example.BEND2webshop.security.ConcreteUserDetails;
import org.example.BEND2webshop.services.PurchaseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PlacePurchaseController.class)
@ActiveProfiles("test")
class PlacePurchaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PurchaseService purchaseService;

    @Test
    @WithMockUser
    void shouldPlacePurchaseAndRedirectToPurchases() throws Exception {

        Long productId = 1L;

        AppUser mockUser = new AppUser();
        mockUser.setId(UUID.randomUUID());
        mockUser.setUsername("mocker");
        mockUser.setPassword("mockpassword");
        mockUser.setEnabled(true);
        mockUser.setRoles(List.of(new UserRole(UUID.randomUUID(), "USER")));

        ConcreteUserDetails userDetails = new ConcreteUserDetails(mockUser);

        mockMvc.perform(post("/products/{productId}/buy", productId)
                        .with(csrf())
                        .with(user(userDetails)))
            .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/purchases"))
                .andExpect(flash().attribute("feedbackContent", "Order placed. Thank you for your purchase!"))
                .andExpect(flash().attribute("feedbackType", "success"));

        verify(purchaseService, times(1)).placePurchase(productId, mockUser);
    }

    @Test
    void shouldRequireAuthenticatedUser() throws Exception {
        mockMvc.perform(post("/products/{productId}/buy", 1L))
                .andExpect(status().isForbidden());

        verify(purchaseService, never()).placePurchase(any(), any());
    }
}