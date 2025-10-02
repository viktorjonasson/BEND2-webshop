package org.example.BEND2webshop.controllers;

import org.example.BEND2webshop.models.AppUser;
import org.example.BEND2webshop.models.UserRole;
import org.example.BEND2webshop.security.ConcreteUserDetails;
import org.example.BEND2webshop.services.PurchaseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PurchasesController.class)
@ActiveProfiles("test")
class DeletePurchaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PurchaseService purchaseService;

    @Test
    void shouldDeletePurchaseAndRedirectToPurchasesWhenAdmin() throws Exception {

        Long purchaseId = 1L;

        AppUser mockAdminUser = new AppUser();
        mockAdminUser.setId(UUID.randomUUID());
        mockAdminUser.setUsername("admin");
        mockAdminUser.setPassword("adminpassword");
        mockAdminUser.setEnabled(true);
        mockAdminUser.setRoles(List.of(new UserRole(UUID.randomUUID(), "admin")));

        ConcreteUserDetails adminUserDetails = new ConcreteUserDetails(mockAdminUser);

        mockMvc.perform(post("/purchases/delete")
                        .param("purchaseId", purchaseId.toString())
                        .with(csrf())
                        .with(user(adminUserDetails)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/purchases"));

        verify(purchaseService, times(1)).deletePurchase(purchaseId);
    }

    @Test
    void shouldRequireAuthenticatedUser() throws Exception {
        mockMvc.perform(post("/purchases/delete")
                        .param("purchaseId", "1"))
                .andExpect(status().isForbidden());

        verify(purchaseService, never()).deletePurchase(any());
    }

    @Test
    void shouldDeletePurchase() {
        Long purchaseId = 1L;
        purchaseService.deletePurchase(purchaseId);
        verify(purchaseService, times(1)).deletePurchase(purchaseId);
    }
}
