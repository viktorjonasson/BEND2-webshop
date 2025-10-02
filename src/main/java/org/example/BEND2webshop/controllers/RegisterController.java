package org.example.BEND2webshop.controllers;

import jakarta.validation.Valid;
import org.example.BEND2webshop.dtos.UserDto;
import org.example.BEND2webshop.exceptions.UsernameNotAvailableException;
import org.example.BEND2webshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Set;

@Controller
public class RegisterController {
    public static final String INVALID_USERNAME_OR_PASSWORD = "Invalid input, username and password must be between 2-30 characters.",
            NOT_PERMITTED = "Not permitted.",
            ACCOUNT_CREATED = "Account created.",
            SUCCESS = "success",
            ERROR = "error",
            FEEDBACK_CONTENT = "feedbackContent",
            FEEDBACK_TYPE = "feedbackType";

    @Autowired
    UserService userService;

    @GetMapping("/register")
    public String register() {
        return "register";
    }


    @PostMapping("/register")
    public String register(@ModelAttribute @Valid UserDto user, RedirectAttributes redirectAttributes) {
        if (user.getRole().contains("admin"))
            throw new AuthorizationDeniedException(NOT_PERMITTED);
        userService.saveUser(user.getUsername(), Set.of(user.getRole()), user.getPassword());
        redirectAttributes.addFlashAttribute(FEEDBACK_CONTENT, ACCOUNT_CREATED);
        redirectAttributes.addFlashAttribute(FEEDBACK_TYPE, SUCCESS);
        return "redirect:register";
    }

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/register-admin")
    public String registerAdmin(@ModelAttribute @Valid UserDto user, RedirectAttributes redirectAttributes) {
        userService.saveUser(user.getUsername(), Set.of(user.getRole()), user.getPassword());
        redirectAttributes.addFlashAttribute(FEEDBACK_CONTENT, ACCOUNT_CREATED);
        redirectAttributes.addFlashAttribute(FEEDBACK_TYPE, SUCCESS);
        return "redirect:register";
    }

    @ExceptionHandler(UsernameNotAvailableException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String usernameNotAvailable(Model model, UsernameNotAvailableException exception) {
        model.addAttribute(FEEDBACK_CONTENT, exception.getMessage());
        model.addAttribute(FEEDBACK_TYPE, ERROR);
        return "register";
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String forbiddenRole(Model model, AuthorizationDeniedException e) {
        model.addAttribute(FEEDBACK_CONTENT, e.getMessage());
        model.addAttribute(FEEDBACK_TYPE, ERROR);
        return "register";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String invalidArguments(Model model) {
        model.addAttribute(FEEDBACK_CONTENT, INVALID_USERNAME_OR_PASSWORD);
        model.addAttribute(FEEDBACK_TYPE, ERROR);
        return "register";
    }
}
