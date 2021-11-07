package org.example.controller;

import org.example.entity.Farm;
import org.example.entity.auth.User;
import org.example.exception.UserNotFoundException;
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
    public String displayFarmCreation(Principal principal, @ModelAttribute("farm") Farm farm, Model model) throws UserNotFoundException {
        model.addAttribute("user", userService.findByLogin(principal.getName()));
        return "addFarm.html";
    }
    
    
    @PostMapping(value = "/new")
    public String createFarm(Principal principal, @Valid @ModelAttribute("farm") Farm farm, BindingResult result, Model model) throws UserNotFoundException {
        User user = userService.findByLogin(principal.getName());
        if (result.hasErrors()) {
            model.addAttribute("user", userService.findByLogin(principal.getName()));
            return "addFarm.html";
        } else {
            farmService.addFarm(user, farm);
            return "redirect:/farms";
        }
    }
    
    @PatchMapping(value = "/{id}")
    public String updateFarm(@Valid @ModelAttribute("farm") Farm farm, BindingResult result, @PathVariable("id") Long id, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", farmService.findFarmById(id).getUser());
            return "redirect:/update/farms/{id}";
        } else {
            farmService.updateFarm(id, farm);
            return "redirect:/farms";
        }
    }
    
    @DeleteMapping(value = "/{id}")
    public String deleteFarm(Principal principal, @PathVariable("id") Long id) throws UserNotFoundException {
        User user = userService.findByLogin(principal.getName());
        farmService.deleteFarm(user, id);
        return "redirect:/farms";
    }
}
