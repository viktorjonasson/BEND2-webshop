package org.example.BEND2webshop.controllers;

import jakarta.validation.Valid;
import org.example.BEND2webshop.dtos.UserDto;
import org.example.BEND2webshop.models.AppUser;
import org.example.BEND2webshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Set;

@Controller
public class RegisterController {

    @Autowired
    UserService userService;

    @GetMapping("/register")
    public String register() {
        return "register";
    }


    @PostMapping("/register")
    public String register(@ModelAttribute @Valid UserDto user, Model model) {
        userService.saveUser(user.getUsername(), Set.of(user.getRole()), user.getPassword());
        model.addAttribute("feedback", "success");
        return "register";
    }

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/register-admin")
    public String registerAdmin(@ModelAttribute @Valid UserDto user, Model model) {
        userService.saveUser(user.getUsername(), Set.of(user.getRole()), user.getPassword());
        model.addAttribute("feedback", "success");
        return "redirect:register";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String illegalArgument(Model model, IllegalArgumentException exception) {
        model.addAttribute("feedback", exception.getMessage());
        return "register";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String invalidArguments(Model model) {
        model.addAttribute("feedback", "Invalid input, username and password must be between 2-30 characters.");
        return "register";
    }
}
