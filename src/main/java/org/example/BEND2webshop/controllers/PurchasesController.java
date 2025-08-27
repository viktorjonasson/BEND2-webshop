package org.example.BEND2webshop.controllers;

import org.springframework.ui.Model;
import org.example.BEND2webshop.services.PurchaseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/purchases")

public class PurchasesController {

    private final PurchaseService purchaseService;

    public PurchasesController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping
    public String getPurchases(Model model) {
        model.addAttribute("purchases", purchaseService.getAllPurchases());
        return "purchases";
    }
}
