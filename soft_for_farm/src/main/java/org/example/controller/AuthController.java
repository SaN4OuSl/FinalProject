package org.example.controller;

import org.example.entity.auth.User;
import org.example.exception.DuplicateUserLogin;
import org.example.exception.UserPasswordSmall;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class AuthController {
    
    private final UserService userService;
    
    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/registration")
    public String displayRegisterForm(@Valid @ModelAttribute("user") User user) {
        return "registration.html";
    }
    
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, @RequestParam(value = "logout", required = false) String logout, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Your username or password is invalid! Please try again.");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "You have been logged out successfully!");
        }
        
        return "login.html";
    }
    
    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("user") User user, BindingResult result) throws DuplicateUserLogin, UserPasswordSmall {
        if (result.hasErrors()) {
            return "registration.html";
        }
        userService.registration(user);
        return "redirect:/login";
    }
    
    @GetMapping(value = {"/", "/home"})
    public String home(Principal principal, Model model) {
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        } else {
            model.addAttribute("username", null);
        }
        return "index.html";
    }
}