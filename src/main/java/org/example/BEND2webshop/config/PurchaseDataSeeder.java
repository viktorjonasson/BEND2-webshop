package org.example.BEND2webshop.config;

import org.example.BEND2webshop.models.AppUser;
import org.example.BEND2webshop.repositories.ProductRepository;
import org.example.BEND2webshop.repositories.PurchaseRepository;
import org.example.BEND2webshop.repositories.UserRepository;
import org.example.BEND2webshop.services.PurchaseService;
import org.springframework.stereotype.Service;

@Service
public class PurchaseDataSeeder {

    PurchaseRepository purchaseRepository;
    ProductRepository productRepository;
    UserRepository userRepository;
    PurchaseService purchaseService;

    public PurchaseDataSeeder(PurchaseRepository purchaseRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.purchaseRepository = purchaseRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.purchaseService = new PurchaseService(purchaseRepository, productRepository);
    }


    public void Seed() {
        if (purchaseRepository.findAll().isEmpty()) {

            AppUser customer = userRepository.findByUsernameIgnoreCase("Yahya");

            if (customer != null) {
                purchaseService.placePurchase(productRepository.findAll().get(0).getId(), customer);
                purchaseService.placePurchase(productRepository.findAll().get(0).getId(), customer);
                purchaseService.placePurchase(productRepository.findAll().get(0).getId(), customer);
                purchaseService.placePurchase(productRepository.findAll().get(0).getId(), customer);
                purchaseService.placePurchase(productRepository.findAll().get(1).getId(), customer);
                purchaseService.placePurchase(productRepository.findAll().get(2).getId(), customer);
                purchaseService.placePurchase(productRepository.findAll().get(3).getId(), customer);
            }
        }
    }
}
