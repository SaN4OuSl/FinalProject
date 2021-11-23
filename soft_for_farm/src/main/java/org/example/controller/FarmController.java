package org.example.controller;

import org.example.entity.Farm;
import org.example.entity.User;
import org.example.exception.farm.AccessToFarmException;
import org.example.exception.farm.FarmNotFoundException;
import org.example.exception.user.UserNotFoundException;
import org.example.service.FarmService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String displayFarmForm(Principal principal, @ModelAttribute("farm") Farm farm, Model model) {
        try {
            model.addAttribute("user", userService.findByLogin(principal.getName()));
            return "addFarm.html";
        } catch (UserNotFoundException e) {
            model.addAttribute("errorMessage", "User not found");
            return "login.html";
        }
    }
    
    @GetMapping()
    public String farms(Principal principal, Model model, @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 5) Pageable pageable) {
        User user;
        try {
            user = userService.findByLogin(principal.getName());
        } catch (UserNotFoundException e) {
            model.addAttribute("errorMessage", "User with this login not found");
            return "error.html";
        }
        Page<Farm> pageFarm = farmService.findAllPageable(user, pageable);
        model.addAttribute("page", pageFarm);
        model.addAttribute("currentUser", user);
        return "farms.html";
    }
    
    @PostMapping(value = "/new")
    public String createFarm(Principal principal, @Valid @ModelAttribute("farm") Farm farm, BindingResult result, Model model) {
        User user;
        try {
            user = userService.findByLogin(principal.getName());
            if (result.hasErrors()) {
                model.addAttribute("user", user);
                return "addFarm.html";
            } else {
                farmService.addFarm(user, farm);
                return "redirect:/farm";
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
                User user = userService.findByLogin(principal.getName());
                model.addAttribute("user", user);
            } catch (UserNotFoundException e) {
                model.addAttribute("errorMessage", "User not found");
                return "error.html";
            }
            return "redirect:/update/farms/{id}";
        } else {
            try {
                User user = userService.findByLogin(principal.getName());
                farmService.updateFarm(user, id, farm);
            } catch (FarmNotFoundException e) {
                model.addAttribute("errorMessage", "Farm with this id not found");
                return "error.html";
            } catch (AccessToFarmException e) {
                model.addAttribute("errorMessage", "You don't have access to this farm");
                return "error.html";
            } catch (UserNotFoundException e) {
                model.addAttribute("errorMessage", "User not found");
                return "error.html";
            }
            return "redirect:/farm";
        }
    }
    
    @DeleteMapping(value = "/{id}")
    public String deleteFarm(Principal principal, @PathVariable("id") Long id, Model model) {
        try {
            User user = userService.findByLogin(principal.getName());
            farmService.deleteFarm(user, id);
            return "redirect:/farm";
        } catch (UserNotFoundException e) {
            model.addAttribute("errorMessage", "User not found");
            return "login.html";
        } catch (FarmNotFoundException | AccessToFarmException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "login.html";
        }
    }
    
    @GetMapping(value = "/{year}")
    public String findFarmsByYear(Principal principal, Model model, @PathVariable String year, @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 5) Pageable pageable) {
        User user;
        try {
            user = userService.findByLogin(principal.getName());
        } catch (UserNotFoundException e) {
            model.addAttribute("errorMessage", "User with this lodin not found");
            return "error.html";
        }
        Page<Farm> pageFarm = farmService.findFarmsByYear(year, user, pageable);
        model.addAttribute("page", pageFarm);
        model.addAttribute("currentUser", user);
        return "farms.html";
    }
    
    @GetMapping(value = "/findByFarmName/{farmName}")
    public String findFarmsByFarmName(Principal principal, Model model, @PathVariable String farmName, @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 5) Pageable pageable) {
        User user;
        try {
            user = userService.findByLogin(principal.getName());
        } catch (UserNotFoundException e) {
            model.addAttribute("errorMessage", "User with this lodin not found");
            return "error.html";
        }
        Page<Farm> pageFarm = farmService.findFarmsByFarmName(farmName, user, pageable);
        model.addAttribute("page", pageFarm);
        model.addAttribute("currentUser", user);
        return "farms.html";
    }
}
