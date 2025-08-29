package org.example.BEND2webshop.controllers;

import jakarta.annotation.security.RolesAllowed;
import org.example.BEND2webshop.models.AppUser;
import org.example.BEND2webshop.security.ConcreteUserDetails;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public String getPurchases(Model model, @AuthenticationPrincipal ConcreteUserDetails userDetails) {
        model.addAttribute("purchases", purchaseService.getPurchasesForCurrentUser(userDetails));
        model.addAttribute("isAdmin", userDetails.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("admin")));
        return "purchases";
    }

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/delete")
    public String deletePurchase(@RequestParam Long purchaseId) {
        purchaseService.deletePurchase(purchaseId);
        return "redirect:/purchases";
    }
}
