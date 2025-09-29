package org.example.BEND2webshop.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.example.BEND2webshop.security.ConcreteUserDetails;
import org.example.BEND2webshop.models.AppUser;
import org.example.BEND2webshop.services.PurchaseService;
import org.example.BEND2webshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PlacePurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @PostMapping("/products/{productId}/buy")
    public String placePurchase(@PathVariable Long productId,
                                @AuthenticationPrincipal ConcreteUserDetails userDetails,
                                RedirectAttributes redirectAttributes) {
        AppUser appUser = userDetails.getUser();
        purchaseService.placePurchase(productId, appUser);
        redirectAttributes.addFlashAttribute("feedbackContent", "Order placed. Thank you for your purchase!");
        redirectAttributes.addFlashAttribute("feedbackType", "success");
        return "redirect:/purchases";
    }
}
