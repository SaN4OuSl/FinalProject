package org.example.controller;

import org.example.entity.*;
import org.example.exception.farm.AccessToFarmException;
import org.example.exception.farm.FarmNotFoundException;
import org.example.exception.user.UserNotFoundException;
import org.example.service.*;
import org.example.service.impl.UserDetailsServiceImplementation;
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
import java.util.List;

@Controller
public class MainController {
    
    private final UserService userService;
    private final UserDetailsServiceImplementation userDetailsServiceImplementation;
    private final FarmService farmService;
    private final PlantService plantService;
    private final AnimalService animalService;
    private final TechniqueService techniqueService;
    
    @Autowired
    public MainController(UserService userService, UserDetailsServiceImplementation userDetailsServiceImplementation, FarmService farmService, PlantService plantService, AnimalService animalService, TechniqueService techniqueService) {
        this.userService = userService;
        this.userDetailsServiceImplementation = userDetailsServiceImplementation;
        this.farmService = farmService;
        this.plantService = plantService;
        this.animalService = animalService;
        this.techniqueService = techniqueService;
    }
    
    @GetMapping("/farms")
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
    
    @DeleteMapping("/deleteYourAccount")
    public String deleteYourAccount(Principal principal, Model model) {
        try {
            userService.deleteById(userService.findByLogin(principal.getName()).getId());
            return "redirect:/login";
        } catch (UserNotFoundException e) {
            model.addAttribute("errorMessage", "User not found");
            return "error.html";
        }
    }
    
    @PatchMapping("/updateYourAccount")
    public String updateYourAccount(Principal principal, @Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        try {
            if (!result.hasErrors()) {
                userService.updateUserById(userService.findByLogin(principal.getName()).getId(), user);
                return "redirect:/login";
            } else {
                return "redirect:/home";
            }
        } catch (UserNotFoundException e) {
            model.addAttribute("errorMessage", "User not found");
            return "error.html";
        }
    }
    
    @GetMapping("update/{aa}/{id}")
    public String update(Principal principal,
                         @PathVariable("aa") String action,
                         @PathVariable("id") Long id,
                         Model model, @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 5) Pageable pageable) throws UserNotFoundException {
        User user = userService.findByLogin(principal.getName());
        Farm farm;
        int option = 0;
        if (action.equals("farms")) {
            try {
                farm = farmService.findFarmById(user, id);
                model.addAttribute("farm", farm);
            } catch (FarmNotFoundException e) {
                model.addAttribute("errorMessage", "Farm with this id not found");
                return "error.html";
            } catch (AccessToFarmException e) {
                model.addAttribute("errorMessage", "You don't have access to this farm");
                return "error.html";
            }
            Page<Farm> pageFarm = farmService.findAllPageable(user, pageable);
            model.addAttribute("page", pageFarm);
            model.addAttribute("expense", String.format("%.2f", farmService.expensesCounter(farm)))
                    .addAttribute("profit", String.format("%.2f", farmService.profitCounter(farm)))
                    .addAttribute("netProfit", String.format("%.2f", farmService.netProfitCounter(farm)));
            model.addAttribute("currentUser", user)
                    .addAttribute("option", option);
            return "farms.html";
        }
        if (action.equals("plant")) {
            Plant plant = plantService.findPlantById(id);
            try {
                farm = farmService.findFarmById(user, plant.getFarm().getId());
            } catch (FarmNotFoundException e) {
                model.addAttribute("errorMessage", "Farm with this id not found");
                return "error.html";
            } catch (AccessToFarmException e) {
                model.addAttribute("errorMessage", "You don't have access to this farm");
                return "error.html";
            }
            List<Plant> plants = plantService.findAllPlantsByFarm(farm);
            model.addAttribute("plant", plant);
            model.addAttribute("expense", String.format("%.2f", plantService.expensesCounter(plant)))
                    .addAttribute("profit", String.format("%.2f", plantService.profitCounter(plant)))
                    .addAttribute("netProfit", String.format("%.2f", plantService.netProfitCounter(plant)));
            model.addAttribute("farm", farm)
                    .addAttribute("option", option)
                    .addAttribute("plants", plants);
            return "plants.html";
        }
        if (action.equals("animal")) {
            Animal animal = animalService.findAnimalById(id);
            try {
                farm = farmService.findFarmById(user, animal.getFarm().getId());
            } catch (FarmNotFoundException e) {
                model.addAttribute("errorMessage", "Farm with this id not found");
                return "error.html";
            } catch (AccessToFarmException e) {
                model.addAttribute("errorMessage", "You don't have access to this farm");
                return "error.html";
            }
            List<Animal> animals = animalService.findAllAnimalByFarm(farm);
            model.addAttribute("animal", animal);
            model.addAttribute("expenses", String.format("%.2f", animalService.expensesCounter(animal)))
                    .addAttribute("profit", String.format("%.2f", animalService.profitCounter(animal)))
                    .addAttribute("netProfit", String.format("%.2f", animalService.netProfitCounter(animal)));
            model.addAttribute("farm", farm)
                    .addAttribute("option", option)
                    .addAttribute("animals", animals);
            return "animals.html";
        }
        if (action.equals("technique")) {
            Technique technique = techniqueService.findTechniqueById(id);
            try {
                farm = farmService.findFarmById(user, technique.getFarm().getId());
            } catch (FarmNotFoundException e) {
                model.addAttribute("errorMessage", "Farm with this id not found");
                return "error.html";
            } catch (AccessToFarmException e) {
                model.addAttribute("errorMessage", "You don't have access to this farm");
                return "error.html";
            }
            List<Technique> techniques = techniqueService.findAllTechniquesByFarm(farm);
            model.addAttribute("technique", technique);
            model.addAttribute("expenses", String.format("%.2f", techniqueService.expensesCounter(technique)));
            model.addAttribute("farm", farm)
                    .addAttribute("option", option)
                    .addAttribute("techniques", techniques);
            return "techniques.html";
        }
        if (action.equals("user")) {
            if (userDetailsServiceImplementation.isAdmin(user)) {
                User userUpdate = userService.findUserById(id);
                model.addAttribute("user", userUpdate);
                model.addAttribute("option", option)
                        .addAttribute("page", userService.findAllPageable(user, pageable))
                        .addAttribute("currentUser", user);
                return "users.html";
            } else {
                model.addAttribute("errorMessage", "You dont have enough rights");
                return "error.html";
            }
        }
        model.addAttribute("errorMessage", "Some error");
        return "error.html";
    }
}
