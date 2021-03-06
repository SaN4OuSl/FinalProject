package org.example.controller;

import org.example.entity.User;
import org.example.exception.user.*;
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
        try {
            User user = userService.findByLogin(principal.getName());
            Page<User> pageUser = userService.findAllPageable(pageable);
            model.addAttribute("page", pageUser);
            model.addAttribute("currentUser", user);
            return "users.html";
        } catch (UserNotFoundException e) {
            model.addAttribute("errorMessage", "User with this login not found");
            return "error.html";
        }
    }
    
    @GetMapping("/new")
    public String displayAdminForm(Principal principal, @ModelAttribute("user") User user, Model model) {
        User userAdmin;
        try {
            userAdmin = userService.findByLogin(principal.getName());
        } catch (UserNotFoundException e) {
            model.addAttribute("errorMessage", "User not found");
            return "login.html";
        }
        model.addAttribute("user", userAdmin);
        return "addAdmin.html";
    }
    
    
    @PostMapping(value = "/new")
    public String createAdmin(Principal principal, @Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        try {
            User userAdmin = userService.findByLogin(principal.getName());
            if (result.hasErrors()) {
                model.addAttribute("user", userAdmin);
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
        } catch (NotEnoughRights | UserLoginSmall e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "addAdmin.html";
        }
    }
    
    @DeleteMapping(value = "/user/{id}")
    public String deleteUser(@PathVariable("id") Long id, Model model) {
        try {
            userService.deleteById(id);
            return "redirect:/admin/users";
        } catch (UserNotFoundException e) {
            model.addAttribute("errorMessage", "User not found");
            return "login.html";
        } catch (NotEnoughRights notEnoughRights) {
            model.addAttribute("errorMessage", "You don't have enough rights");
            return "login.html";
        }
    }
    
    @PatchMapping(value = "/userUpdate/{id}")
    public String updateUser(@Valid @ModelAttribute("user") User newUser, BindingResult result, @PathVariable("id") Long id, Model model) {
        if (result.hasErrors()) {
            return "redirect:/update/user/{id}";
        } else {
            try {
                userService.updateById(id, newUser);
            } catch (UserNotFoundException e) {
                model.addAttribute("errorMessage", "User not found");
                return "error.html";
            } catch (NotEnoughRights | UserPasswordSmall | UserLoginSmall | DuplicateUserLogin e) {
                model.addAttribute("errorMessage", e.getMessage());
                return "error.html";
            }
            return "redirect:/admin/users";
        }
    }
    
    @PatchMapping(value = "/addAdminRole/{id}")
    public String addAdminRole(@PathVariable("id") Long id, Model model) {
        try {
            userService.addAdminRole(userService.findUserById(id));
        } catch (UserNotFoundException e) {
            model.addAttribute("errorMessage", "User not found");
            return "error.html";
        }
        return "redirect:/admin/users";
    }
    
    @GetMapping("update/user/{id}")
    public String update(Principal principal,
                         @PathVariable("id") Long id,
                         Model model, @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 5) Pageable pageable) throws UserNotFoundException, NotEnoughRights {
        User userUpdate = userService.findUserById(id);
        model.addAttribute("user", userUpdate);
        model.addAttribute("option", 0)
                .addAttribute("page", userService.findAllPageable(pageable))
                .addAttribute("currentUser", userService.findByLogin(principal.getName()));
        return "users.html";
    }
}
