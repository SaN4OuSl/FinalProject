package org.example.controller;

import org.example.entity.User;
import org.example.exception.user.DuplicateUserLogin;
import org.example.exception.user.UserLoginSmall;
import org.example.exception.user.UserNotFoundException;
import org.example.exception.user.UserPasswordSmall;
import org.example.service.UserService;
import org.example.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class AuthController {
    
    private final UserService userService;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    
    @Autowired
    public AuthController(UserService userService, UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userService = userService;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }
    
    @GetMapping("/registration")
    public String displayRegistrationForm(@Valid @ModelAttribute("user") User user) {
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
    public String registration(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "registration.html";
        }
        try {
            userService.registration(user);
        } catch (DuplicateUserLogin e) {
            model.addAttribute("errorMessage", "This username already exists");
            return "registration.html";
        } catch (UserPasswordSmall e) {
            model.addAttribute("errorMessage", "Password must be have more than 8 characters");
            return "registration.html";
        } catch (UserLoginSmall userLoginSmall) {
            model.addAttribute("errorMessage", "Login must be have more than 2 characters");
            return "registration.html";
        }
        return "redirect:/login";
    }
    
    @GetMapping(value = {"/", "/home"})
    public String home(Principal principal, Model model) {
        try {
            User user = userService.findByLogin(principal.getName());
            if (!userDetailsServiceImpl.isAdmin(user)) {
                model.addAttribute("username", principal.getName());
                model.addAttribute("user", user);
                return "index.html";
            } else {
                model.addAttribute("username", principal.getName());
                model.addAttribute("user", user);
                return "adminHome.html";
            }
        } catch (UserNotFoundException e) {
            model.addAttribute("errorMessage", "User not found");
            return "error.html";
        }
    }
}