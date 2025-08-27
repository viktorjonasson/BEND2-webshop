package org.example.BEND2webshop.services;


import org.example.BEND2webshop.models.AppUser;
import org.example.BEND2webshop.models.Product;
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

    public void placePurchase(Long productId, AppUser appUser) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

        Purchase purchase = Purchase.builder()
                .purchaseDate(LocalDateTime.now())
                .product(product)
                .appUser(appUser)
                .build();

        purchaseRepository.save(purchase);
    }

    //ToDo: Metod för att returnera en enskild användares ordrar.
    //Denna metod för ALLA köp, inte en specifik användares.
    public List<PurchaseDto> getAllPurchases(){
        return purchaseRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public void deletePurchase(Long purchaseId) {
        if (!purchaseRepository.existsById(purchaseId)) {
            throw new RuntimeException("Purchase not found with ID: " + purchaseId);
        }
        purchaseRepository.deleteById(purchaseId);
    }

    private PurchaseDto toDto(Purchase p) {
        return new PurchaseDto(
        p.getId(),
        p.getPurchaseDate(),
        p.getProduct().getId(),
        p.getProduct().getTitle(),
        p.getProduct().getPrice(),
        p.getAppUser().getId(),
        p.getAppUser().getUsername()
        );
    }
}
