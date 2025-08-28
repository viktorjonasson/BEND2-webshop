package org.example.BEND2webshop.controllers;

import org.example.BEND2webshop.security.ConcreteUserDetails;
import org.example.BEND2webshop.models.AppUser;
import org.example.BEND2webshop.services.PurchaseService;
import org.example.BEND2webshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PlacePurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @PostMapping("/products/{productId}/buy")
    public String placePurchase(@PathVariable Long productId,
                                @AuthenticationPrincipal ConcreteUserDetails userDetails) {
        AppUser appUser = userDetails.getUser();
        purchaseService.placePurchase(productId, appUser);
        return "redirect:/purchases"; // + user.getId();
    }
}
