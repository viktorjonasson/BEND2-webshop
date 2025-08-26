package org.example.BEND2webshop;

import org.example.BEND2webshop.security.UserDataSeeder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BEND2WebshopApplication {

    @Autowired
    private UserDataSeeder userDataSeeder;

    public static void main(String[] args) {

        SpringApplication.run(BEND2WebshopApplication.class, args);
    }

//    Boostrap database with users
    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            userDataSeeder.Seed();
//            productImportService.fetchAndSaveProducts();
        };
    }
}
