package org.example.BEND2webshop.repositories;

import org.example.BEND2webshop.models.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
