package org.example.BEND2webshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping({"/", "/home"})
    public String home() {
        return "home";
    }

    @GetMapping("/logged-in")
    public String loggedIn() {
        return "logged-in";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
