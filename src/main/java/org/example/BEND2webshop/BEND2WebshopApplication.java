package org.example.BEND2webshop;

import org.example.BEND2webshop.security.UserDataSeeder;
import org.example.BEND2webshop.services.ProductImportService;
import org.example.BEND2webshop.services.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BEND2WebshopApplication {

    @Autowired
    private UserDataSeeder userDataSeeder;
    @Autowired
    private ProductImportService productImportService;
    @Autowired
    private PurchaseService purchaseService;

    public static void main(String[] args) {

        SpringApplication.run(BEND2WebshopApplication.class, args);
    }

    //    Boostrap database with users
    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            userDataSeeder.Seed();
            productImportService.fetchAndSaveProducts();
            purchaseService.SeedPurchases();
        };
    }
}
