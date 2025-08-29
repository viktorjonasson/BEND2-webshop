package org.example.BEND2webshop.repositories;

import org.example.BEND2webshop.models.AppUser;
import org.example.BEND2webshop.models.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {


    // Admin

    List<Purchase> findAll();

    List<Purchase> findByAppUser(AppUser currentUser);


    // User

//    List<Purchase> findByUserId(Long id);

}
