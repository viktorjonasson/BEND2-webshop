package org.example.BEND2webshop.repositories;

import org.example.BEND2webshop.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
