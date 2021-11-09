package org.example.controller;

import org.example.exception.user.AccessToUserException;
import org.example.exception.user.DuplicateUserLogin;
import org.example.exception.user.UserNotFoundException;
import org.example.exception.user.UserPasswordSmall;
import org.example.model.User;
import org.example.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;


@Controller
@RequestMapping("/admin")
public class AdminController {
    
    private final UserService userService;
    
    public AdminController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/users")
    public String users(Principal principal, Model model, @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 5) Pageable pageable) {
        User user;
        try {
            user = userService.findByLogin(principal.getName());
        } catch (UserNotFoundException e) {
            model.addAttribute("errorMessage", "User with this login not found");
            return "error.html";
        }
        Page<User> pageUser = userService.findAllPageable(user, pageable);
        model.addAttribute("page", pageUser);
        model.addAttribute("currentUser", user);
        return "users.html";
    }
    
    @GetMapping("/new")
    public String displayAdminCreation(Principal principal, @ModelAttribute("user") User user, Model model) {
        try {
            model.addAttribute("user", userService.findByLogin(principal.getName()));
            return "addAdmin.html";
        } catch (UserNotFoundException e) {
            model.addAttribute("errorMessage", "User not found");
            return "login.html";
        }
    }
    
    
    @PostMapping(value = "/new")
    public String createAdmin(Principal principal, @Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        try {
            if (result.hasErrors()) {
                model.addAttribute("user", userService.findByLogin(principal.getName()));
                return "addAdmin.html";
            } else {
                userService.registrationAdmin(user);
                return "redirect:/admin/users";
            }
        } catch (UserNotFoundException e) {
            model.addAttribute("errorMessage", "User not found");
            return "login.html";
        } catch (DuplicateUserLogin duplicateUserLogin) {
            model.addAttribute("errorMessage", "Duplicate user");
            return "addAdmin.html";
        } catch (UserPasswordSmall userPasswordSmall) {
            model.addAttribute("errorMessage", "Password is small");
            return "addAdmin.html";
        }
    }
    
    @DeleteMapping(value = "/user/{id}")
    public String deleteUser(Principal principal, @PathVariable("id") Long id, Model model) {
        try {
            userService.deleteById(principal, id);
            return "redirect:/admin/users";
        } catch (UserNotFoundException e) {
            model.addAttribute("errorMessage", "User not found");
            return "login.html";
        } catch (AccessToUserException e) {
            model.addAttribute("errorMessage", "You dont have enough rights");
            return "error.html";
        }
    }
}
