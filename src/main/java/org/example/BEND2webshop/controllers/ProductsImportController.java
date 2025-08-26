package org.example.BEND2webshop.controllers;

import org.example.BEND2webshop.repositories.ProductRepository;
import org.example.BEND2webshop.services.ProductImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductsImportController {

    @Autowired
    ProductRepository productRepository;
    ProductImportService productImportService;

    public ProductsImportController(ProductRepository productRepository, ProductImportService productImportService) {
        this.productRepository = productRepository;
        this.productImportService = productImportService;
    }

    @GetMapping("/fetch")
    public ResponseEntity<?> fetchProducts() {
        try {
            productImportService.fetchAndSaveProducts();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body(productRepository.findAll());
    }
}
