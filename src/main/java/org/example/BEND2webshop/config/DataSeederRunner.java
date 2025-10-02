package org.example.BEND2webshop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class DataSeederRunner implements CommandLineRunner {

    @Autowired
    private UserDataSeeder userDataSeeder;

    @Autowired
    private ProductImportService productImportService;

    @Autowired
    private PurchaseDataSeeder purchaseDataSeeder;

    @Override
    public void run(String... args) throws Exception {
        userDataSeeder.Seed();
        productImportService.fetchAndSaveProducts();
        purchaseDataSeeder.Seed();
    }
}
