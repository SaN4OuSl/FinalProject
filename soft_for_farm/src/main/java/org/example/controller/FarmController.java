package org.example.controller;

import org.example.entity.Farm;
import org.example.entity.auth.User;
import org.example.exception.farm.AccessToFarmException;
import org.example.exception.farm.FarmNotFoundException;
import org.example.exception.user.UserNotFoundException;
import org.example.service.FarmService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/farm")
public class FarmController {
    
    private final FarmService farmService;
    private final UserService userService;
    
    @Autowired
    public FarmController(FarmService farmService, UserService userService) {
        this.farmService = farmService;
        this.userService = userService;
    }
    
    @GetMapping("/new")
    public String displayFarmCreation(Principal principal, @ModelAttribute("farm") Farm farm, Model model) {
        try {
            model.addAttribute("user", userService.findByLogin(principal.getName()));
            return "addFarm.html";
        } catch (UserNotFoundException e) {
            model.addAttribute("errorMessage", "User not found");
            return "login.html";
        }
    }
    
    
    @PostMapping(value = "/new")
    public String createFarm(Principal principal, @Valid @ModelAttribute("farm") Farm farm, BindingResult result, Model model) {
        try {
            User user = userService.findByLogin(principal.getName());
            if (result.hasErrors()) {
                model.addAttribute("user", user);
                return "addFarm.html";
            } else {
                farmService.addFarm(user, farm);
                return "redirect:/farms";
            }
        } catch (UserNotFoundException e) {
            model.addAttribute("errorMessage", "User not found");
            return "login.html";
        }
    }
    
    @PatchMapping(value = "/{id}")
    public String updateFarm(Principal principal, @Valid @ModelAttribute("farm") Farm farm, BindingResult result, @PathVariable("id") Long id, Model model) {
        if (result.hasErrors()) {
            try {
                model.addAttribute("user", farmService.findFarmById(principal, id).getUser());
            } catch (FarmNotFoundException e) {
                model.addAttribute("errorMessage", "Farm with this id not found");
                return "error.html";
            } catch (AccessToFarmException e) {
                model.addAttribute("errorMessage", "You don't have access to this farm");
                return "error.html";
            }
            return "redirect:/update/farms/{id}";
        } else {
            try {
                farmService.updateFarm(principal, id, farm);
            } catch (FarmNotFoundException e) {
                model.addAttribute("errorMessage", "Farm with this id not found");
                return "error.html";
            } catch (AccessToFarmException e) {
                model.addAttribute("errorMessage", "You don't have access to this farm");
                return "error.html";
            }
            return "redirect:/farms";
        }
    }
    
    @DeleteMapping(value = "/{id}")
    public String deleteFarm(Principal principal, @PathVariable("id") Long id, Model model) {
        try {
            User user = userService.findByLogin(principal.getName());
            farmService.deleteFarm(user, id);
            return "redirect:/farms";
        } catch (UserNotFoundException e) {
            model.addAttribute("errorMessage", "User not found");
            return "login.html";
        }
    }
}
