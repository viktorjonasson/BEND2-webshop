package org.example.BEND2webshop.services;


import org.example.BEND2webshop.models.Product;
import org.example.BEND2webshop.models.User;
import org.example.BEND2webshop.repositories.ProductRepository;
import org.example.BEND2webshop.repositories.PurchaseRepository;
import org.example.BEND2webshop.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.example.BEND2webshop.dtos.PurchaseDto;
import org.example.BEND2webshop.models.Purchase;

import java.util.List;


@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public PurchaseService(PurchaseRepository purchaseRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.purchaseRepository = purchaseRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public Long placePurchase(Long productId, Long userId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        Purchase purchase = Purchase.builder()
                .purchaseDate(LocalDateTime.now())
                .product(product)
                .user(user)
                .build();

        Purchase placedPurchase = purchaseRepository.save(purchase);

        return placedPurchase.getId();
    }

    public List<PurchaseDto> getAllPurchases(){
        return purchaseRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    private PurchaseDto toDto(Purchase p) {
        return new PurchaseDto(
        p.getId(),
        p.getProductId(),
        p.getQuantity()
        );
    }
}
