package org.example.BEND2webshop.controllers;

import org.springframework.ui.Model;
import org.example.BEND2webshop.services.PurchaseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;


@Controller
@RequestMapping("/purchases")

public class PurchasesController {

    private final PurchaseService purchaseService;

    public PurchasesController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping
    public String getPurchases(Model model, Authentication authentication) {
        model.addAttribute("purchases", purchaseService.getAllPurchases());
        boolean isAdmin = authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("admin"));
        model.addAttribute("isAdmin", isAdmin);
        return "purchases";
    }

    @PostMapping("/delete")
    public String deletePurchase(@RequestParam Long purchaseId) {
        purchaseService.deletePurchase(purchaseId);
        return "redirect:/purchases";
    }
}
