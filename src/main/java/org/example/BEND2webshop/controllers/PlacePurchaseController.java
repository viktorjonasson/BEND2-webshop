package org.example.BEND2webshop.controllers;

import org.example.BEND2webshop.models.User;
import org.example.BEND2webshop.security.ConcreteUserDetails;
import org.example.BEND2webshop.services.ProductService;
import org.example.BEND2webshop.models.AppUser;
import org.example.BEND2webshop.services.PurchaseService;
import org.example.BEND2webshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PlacePurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private UserService userService;

    @PostMapping("/products/{productId}/buy")
    public String placePurchase(@PathVariable Long productId,
                                @AuthenticationPrincipal ConcreteUserDetails userDetails) {
        User user = userDetails.getUser();
        purchaseService.placePurchase(productId, user);
        return "redirect:/purchases/"; // + user.getId();
        //ToDo: redirect bara till den aktuella användarens ordrar (dvs gör en egen HTML som bara listar ordrar från inloggad user)
    }
}
