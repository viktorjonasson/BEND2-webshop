package org.example.BEND2webshop.services;


import org.example.BEND2webshop.models.AppUser;
import org.example.BEND2webshop.models.UserRole;
import org.example.BEND2webshop.repositories.ProductRepository;
import org.example.BEND2webshop.repositories.PurchaseRepository;
import org.example.BEND2webshop.security.ConcreteUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceGetPurchasesTest {

    @Mock
    PurchaseRepository purchaseRepository;
    @Mock
    ProductRepository productRepository;

    PurchaseService service;

    private ConcreteUserDetails principal(String role) {
        var appUser = new AppUser();
        appUser.setId(UUID.randomUUID());
        appUser.setUsername("andy");
        appUser.setEnabled(true);
        appUser.setRoles(List.of(new UserRole(UUID.randomUUID(), role)));
        return new ConcreteUserDetails(appUser);
    }

    @BeforeEach
    void setUp() {
        service = new PurchaseService(purchaseRepository, productRepository);
    }

    @Test
    void userCalls_findByAppUser(){
        var userPrincipal = principal("USER");
        when(purchaseRepository.findByAppUser(userPrincipal.getUser())).thenReturn(List.of());

        service.getPurchasesForCurrentUser(userPrincipal);

        verify(purchaseRepository, times(1)).findByAppUser(userPrincipal.getUser());
        verify(purchaseRepository, never()).findAll();
    }

    @Test
    void adminCalls_findALL(){
        var userPrincipal = principal("admin");
        when(purchaseRepository.findAll()).thenReturn(List.of());

        service.getPurchasesForCurrentUser(userPrincipal);

        verify(purchaseRepository, times(1)).findAll();
        verify(purchaseRepository, never()).findByAppUser(any());
    }
}