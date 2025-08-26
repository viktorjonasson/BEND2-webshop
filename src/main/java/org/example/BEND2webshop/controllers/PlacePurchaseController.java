package org.example.BEND2webshop.controllers;

import org.example.BEND2webshop.services.ProductService;
import org.example.BEND2webshop.services.PurchaseService;
import org.example.BEND2webshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PlacePurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private UserService userService;

    @PostMapping("/products/{id}/buy")
    public String placePurchase(@PathVariable ("id") Long productId, Long userId) {
        Long purchaseId = purchaseService.placePurchase(productId, userId);
        return "redirect:/orders/" + purchaseId;
    }

    /*
    Skapar order: skapar ID
    Tar in: product ID
    Add to model: redirect to /orders and populate with data
     */

}
