package org.example.BEND2webshop.controllers;

import org.example.BEND2webshop.models.User;
import org.example.BEND2webshop.services.ProductService;
import org.example.BEND2webshop.services.PurchaseService;
import org.example.BEND2webshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@Controller
public class PlacePurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private UserService userService;

    @PostMapping("/products/{productId}/buy/")
    public String placePurchase(@PathVariable Long productId,
                                @ModelAttribute User user) {
//        TODO: ändra {userId}
        Long purchaseId = purchaseService.placePurchase(productId, user.getId());
        return "redirect:/orders/" + purchaseId;
    }
}
