package org.example.BEND2webshop.controllers;

import org.example.BEND2webshop.dtos.PurchaseDto;
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

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import java.util.List;
import java.util.UUID;

@WebMvcTest(controllers = PurchasesController.class)
@ActiveProfiles("test")
public class PurchasesControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    private PurchaseService purchaseService;
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
    @WithMockUser
    void shouldAllowUserToBrowseToPurchasesPage() throws Exception {
        AppUser mockUser = new AppUser();
        mockUser.setId(UUID.randomUUID());
        mockUser.setUsername("testuser");
        mockUser.setPassword("testpassword");
        mockUser.setEnabled(true);
        mockUser.setRoles(List.of(new UserRole(UUID.randomUUID(), "USER")));

        ConcreteUserDetails userDetails = new ConcreteUserDetails(mockUser);
    void authenticated_userSets_isAdminFalse () throws Exception {
        var principal = userDetails(ROLE_USER);
        when(purchaseService.getPurchasesForCurrentUser(principal)).thenReturn(List.of());

        List<PurchaseDto> mockPurchases = List.of();
        when(purchaseService.getPurchasesForCurrentUser(userDetails)).thenReturn(mockPurchases);

        mockMvc.perform(get("/purchases")
                        .with(user(userDetails)))
        mockMvc.perform(get("/purchases").with(user(principal)))
                .andExpect(status().isOk())
                .andExpect(view().name("purchases"))
                .andExpect(model().attribute("purchases", mockPurchases))
                .andExpect(model().attributeExists("purchases"))
                .andExpect(model().attribute("isAdmin", false));
        verify(purchaseService, times(1)).getPurchasesForCurrentUser(userDetails);

        verify(purchaseService, times(1)).getPurchasesForCurrentUser(principal);
    }

    @Test
    @WithMockUser
    void shouldShowAdminFlagForAdminUsers() throws Exception {
        AppUser mockAdminUser = new AppUser();
        mockAdminUser.setId(UUID.randomUUID());
        mockAdminUser.setUsername("adminuser");
        mockAdminUser.setPassword("adminpassword");
        mockAdminUser.setEnabled(true);
        mockAdminUser.setRoles(List.of(new UserRole(UUID.randomUUID(), "admin")));

        ConcreteUserDetails adminUserDetails = new ConcreteUserDetails(mockAdminUser);
    void authenticated_userSets_isAdminTrue() throws Exception {
        var principal = userDetails(ROLE_ADMIN);
        when(purchaseService.getPurchasesForCurrentUser(principal)).thenReturn(List.of());

        List<PurchaseDto> mockPurchases = List.of();
        when(purchaseService.getPurchasesForCurrentUser(adminUserDetails)).thenReturn(mockPurchases);

        mockMvc.perform(get("/purchases")
                        .with(user(adminUserDetails)))
        mockMvc.perform(get("/purchases").with(user(principal)))
                .andExpect(status().isOk())
                .andExpect(view().name("purchases"))
                .andExpect(model().attribute("purchases", mockPurchases))
                .andExpect(model().attributeExists("purchases"))
                .andExpect(model().attribute("isAdmin", true));

        verify(purchaseService, times(1)).getPurchasesForCurrentUser(adminUserDetails);
    }

    @Test
    void shouldRequireAuthenticatedUserToAccessPurchasesPage() throws Exception {
        mockMvc.perform(get("/purchases"))
                .andExpect(status().isUnauthorized());

        verify(purchaseService, never()).getPurchasesForCurrentUser(any());
        verify(purchaseService, times(1)).getPurchasesForCurrentUser(principal);
    }
}
