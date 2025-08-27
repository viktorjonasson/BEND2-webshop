package org.example.BEND2webshop.services;


import org.example.BEND2webshop.models.Product;
import org.example.BEND2webshop.models.User;
import org.example.BEND2webshop.repositories.ProductRepository;
import org.example.BEND2webshop.repositories.PurchaseRepository;
import org.example.BEND2webshop.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import org.example.BEND2webshop.dtos.PurchaseDto;
import org.example.BEND2webshop.models.Purchase;

import java.util.List;
import java.util.UUID;


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

    public Long placePurchase(Long productId, User user) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

        Purchase purchase = Purchase.builder()
                .purchaseDate(LocalDateTime.now())
                .product(product)
                .user(user)
                .build();

        return purchaseRepository.save(purchase).getId();
    }


    //ToDo: Metod för att returnera en enskild användares ordrar.
    //Denna metod för ALLA köp, inte en specifik användares.
    public List<PurchaseDto> getAllPurchases(){
        return purchaseRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    private PurchaseDto toDto(Purchase p) {
        return new PurchaseDto(
        p.getId(),
        p.getPurchaseDate(),
        p.getProduct().getId(),
        p.getProduct().getTitle(),
        p.getProduct().getPrice(),
        p.getUser().getId(),
        p.getUser().getUsername()
        );
    }
}
