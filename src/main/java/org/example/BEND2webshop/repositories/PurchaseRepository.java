package org.example.BEND2webshop.repositories;

import org.example.BEND2webshop.models.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PurchaseRepository extends JpaRepository<Purchase, UUID> {


    // Admin

    List<Purchase> findAll();


    // User

//    List<Purchase> findByUserId(Long id);

}
