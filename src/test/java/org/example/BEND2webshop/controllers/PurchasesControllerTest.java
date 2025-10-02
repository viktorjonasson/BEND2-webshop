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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@WebMvcTest(controllers = PurchasesController.class)
@ActiveProfiles("test")
public class PurchasesControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    PurchaseService purchaseService;

    private static final String ROLE_USER  = "USER";
    private static final String ROLE_ADMIN = "admin";



    private ConcreteUserDetails userDetails(String roleName) {
        var appUser = new AppUser();
        appUser.setId(UUID.randomUUID());
        appUser.setUsername("andy");
        appUser.setPassword("pass");
        appUser.setEnabled(true);
        appUser.setRoles(List.of(new UserRole(UUID.randomUUID(), roleName)));
        return new ConcreteUserDetails(appUser);
    }


    @Test
    void unauthenticated_returns401() throws Exception {
        mockMvc.perform(get("/purchases"))
                .andExpect(status().isUnauthorized());

        verifyNoInteractions(purchaseService);
    }

    @Test
    void authenticated_userGetsView() throws Exception {
        var principal = userDetails("USER");
        when(purchaseService.getPurchasesForCurrentUser(principal)).thenReturn(List.of());

        mockMvc.perform(get("/purchases").with(user(principal)))
                .andExpect(status().isOk())
                .andExpect(view().name("purchases"))
                .andExpect(model().attributeExists("purchases"));

        verify(purchaseService, times(1)).getPurchasesForCurrentUser(principal);
    }

    @Test
    void authenticated_userSets_isAdminFalse () throws Exception {
        var principal = userDetails(ROLE_USER);
        when(purchaseService.getPurchasesForCurrentUser(principal)).thenReturn(List.of());

        mockMvc.perform(get("/purchases").with(user(principal)))
                .andExpect(status().isOk())
                .andExpect(view().name("purchases"))
                .andExpect(model().attributeExists("purchases"))
                .andExpect(model().attribute("isAdmin", false));

        verify(purchaseService, times(1)).getPurchasesForCurrentUser(principal);
    }

    @Test
    void authenticated_userSets_isAdminTrue() throws Exception {
        var principal = userDetails(ROLE_ADMIN);
        when(purchaseService.getPurchasesForCurrentUser(principal)).thenReturn(List.of());

        mockMvc.perform(get("/purchases").with(user(principal)))
                .andExpect(status().isOk())
                .andExpect(view().name("purchases"))
                .andExpect(model().attributeExists("purchases"))
                .andExpect(model().attribute("isAdmin", true));

        verify(purchaseService, times(1)).getPurchasesForCurrentUser(principal);
    }
}
