package org.example.BEND2webshop.services;


import org.example.BEND2webshop.exceptions.ProductNotFoundException;
import org.example.BEND2webshop.models.AppUser;
import org.example.BEND2webshop.models.Product;
import org.example.BEND2webshop.repositories.ProductRepository;
import org.example.BEND2webshop.repositories.PurchaseRepository;
import org.example.BEND2webshop.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import org.example.BEND2webshop.dtos.PurchaseDto;
import org.example.BEND2webshop.models.Purchase;

import java.util.List;
import java.util.Optional;


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
                .orElseThrow(() -> new ProductNotFoundException(productId));

        Purchase purchase = Purchase.builder()
                .purchaseDate(LocalDateTime.now())
                .product(product)
                .appUser(appUser)
                .build();

        purchaseRepository.save(purchase);
    }

    public List<PurchaseDto> getPurchasesForCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("admin"));
        if (isAdmin) {
            return purchaseRepository.findAll()
                    .stream()
                    .map(this::toDto)
                    .toList();
        } else {
            String username = authentication.getName();
            AppUser currentUser = userRepository.findByUsernameIgnoreCase(username);
            if (currentUser == null) {
                throw new RuntimeException("User not found: " + username);
            }
            return purchaseRepository.findByAppUser(currentUser)
                    .stream()
                    .map(this::toDto)
                    .toList();
        }


//        return purchaseRepository.findAll()
//                .stream()
//                .map(this::toDto)
//                .toList();
    }

    public void deletePurchase(Long purchaseId) {
        Optional<Purchase> purchase = purchaseRepository.findById(purchaseId);
        if (purchase.isPresent()) {
            boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                    .anyMatch(role -> role.getAuthority().equals("admin"));
            if (isAdmin) {
                purchaseRepository.deleteById(purchaseId);
            } else {
                throw new RuntimeException("Only admins can delete a purchase" + purchaseId);
            }
        } else {
            throw new RuntimeException("Purchase not found with ID: " + purchaseId);
        }
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
