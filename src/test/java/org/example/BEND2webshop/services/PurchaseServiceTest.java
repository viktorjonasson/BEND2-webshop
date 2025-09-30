package org.example.BEND2webshop.services;

import org.example.BEND2webshop.models.*;
import org.example.BEND2webshop.repositories.ProductRepository;
import org.example.BEND2webshop.repositories.PurchaseRepository;
import org.example.BEND2webshop.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private UserRepository userRepository;

    private PurchaseService purchaseService;

    @BeforeEach
    void setUp() {
        purchaseService = new PurchaseService(purchaseRepository, productRepository, userRepository);
    }

    @Test
    void shouldPlacePurchase() {
        Long productId = 1L;

        Product mockProduct = new Product();
        mockProduct.setId(productId);

        AppUser mockUser = new AppUser();
        mockUser.setId(UUID.randomUUID());

        when(productRepository.findById(productId))
        .thenReturn(Optional.of(mockProduct));

        ArgumentCaptor<Purchase> purchaseCaptor = ArgumentCaptor.forClass(Purchase.class);

        purchaseService.placePurchase(productId, mockUser);

        verify(purchaseRepository).save(purchaseCaptor.capture());

        Purchase savedPurchase = purchaseCaptor.getValue();
        assertEquals(productId, savedPurchase.getProduct().getId());
        assertNotEquals(0, savedPurchase.getId());
        assertEquals(mockUser, savedPurchase.getAppUser());
        assertEquals(mockUser.getId(), savedPurchase.getAppUser().getId());
        assertNotNull(savedPurchase.getPurchaseDate());
    }
}