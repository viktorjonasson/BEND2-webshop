package org.example.BEND2webshop.services;

import org.example.BEND2webshop.dtos.PurchaseDto;
import org.example.BEND2webshop.models.Purchase;
import org.example.BEND2webshop.repositories.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    public List<PurchaseDto> getAllPurchases(){
        return purchaseRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    private PurchaseDto toDto(Purchase p) {
        return new PurchaseDto(
        p.getId(),
        p.getProductId(),
        p.getQuantity() != null ? p.getQuantity() : 1
        );
    }
}
