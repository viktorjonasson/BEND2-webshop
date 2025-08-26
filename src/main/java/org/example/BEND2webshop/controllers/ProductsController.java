package org.example.BEND2webshop.controllers;

import org.example.BEND2webshop.dtos.ProductDto;
import org.example.BEND2webshop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProductsController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public String products(Model model) {
        List<ProductDto> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "products";
    }
}
