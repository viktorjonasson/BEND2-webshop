package org.example.BEND2webshop.repositories;

import org.example.BEND2webshop.models.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {


    // Admin

    List<Purchase> findAll();


    // User

}
