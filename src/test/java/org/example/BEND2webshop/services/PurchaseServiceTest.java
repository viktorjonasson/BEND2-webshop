package org.example.BEND2webshop.services;

import org.example.BEND2webshop.dtos.PurchaseDto;
import org.example.BEND2webshop.exceptions.ProductNotFoundException;
import org.example.BEND2webshop.models.*;
import org.example.BEND2webshop.repositories.ProductRepository;
import org.example.BEND2webshop.repositories.PurchaseRepository;
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

    private PurchaseService purchaseService;

    @BeforeEach
    void setUp() {
        purchaseService = new PurchaseService(purchaseRepository, productRepository);
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

    @Test
    void shouldNotPlacePurchaseForNonExistingProduct() {
        Long productId = 1L;
        AppUser mockUser = new AppUser();

        when(productRepository.findById(productId))
                .thenReturn(Optional.empty());

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> {
            purchaseService.placePurchase(productId, mockUser);
        });

        assertNotNull(exception.getMessage());

        verify(purchaseRepository, never()).save(any());
    }

    @Test
    void shouldDecreasePurchaseCountWhenDeletingExistingPurchase() {
        Long purchaseId = 1L;

        Purchase mockPurchase = new Purchase();
        mockPurchase.setId(purchaseId);

        when(purchaseRepository.findById(purchaseId))
                .thenReturn(Optional.of(mockPurchase));

        purchaseService.deletePurchase(purchaseId);

        verify(purchaseRepository).deleteById(purchaseId);
    }

    @Test
    void shouldThrowWhenDeletingNonExistingPurchase() {
        Long purchaseId = 2902390230L;
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            purchaseService.deletePurchase(purchaseId);
        });
    }

    @Test
    void shouldMapDto() {
        Long purchaseId = 1L;
        Long productId = 2L;
        UUID userId = UUID.randomUUID();
        String productTitle = "Test Product";
        double productPrice = 19.99;

        Product mockProduct = new Product();
        mockProduct.setId(productId);
        mockProduct.setTitle(productTitle);
        mockProduct.setPrice(productPrice);

        AppUser mockUser = new AppUser();
        mockUser.setId(userId);

        Purchase mockPurchase = new Purchase();
        mockPurchase.setId(purchaseId);
        mockPurchase.setProduct(mockProduct);
        mockPurchase.setAppUser(mockUser);
        mockPurchase.setPurchaseDate(java.time.LocalDateTime.of(2023, 10, 1, 12, 0));
        PurchaseDto dto = purchaseService.toDto(mockPurchase);
        assertEquals(purchaseId, dto.getId());
        assertEquals(productId, dto.getProductId());
        assertEquals(productTitle, dto.getProductTitle());
        assertEquals(productPrice, dto.getProductPrice());
        assertEquals(userId, dto.getUserId());
        assertEquals(mockPurchase.getPurchaseDate(), dto.getPurchaseDate());
    }

}