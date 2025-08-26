package org.example.BEND2webshop.services;

import org.example.BEND2webshop.models.Product;
import org.example.BEND2webshop.models.Purchase;
import org.example.BEND2webshop.repositories.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    public Long placePurchase(Long productId, Long userId) {

        //Create order by building a model and return orderId created.
        Purchase purchase = new Purchase();

        // ToDo: Complete model and DTO for purchase

//        purchase.setPurchaseDate(LocalDate.now());
//        purchase.setProductId(productId);
//        purchase.setUserId(userId);

        purchaseRepository.save(purchase);

        /*
        Create an order and save to DB.
        Return order ID to controller.
        Do I need to build a product model or just place product ID in the order row?
         */

        return null; //orderId;
    }
}
