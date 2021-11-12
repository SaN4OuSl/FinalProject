package org.example.controller;

import org.example.exception.user.UserNotFoundException;
import org.example.entity.Farm;
import org.example.entity.Plant;
import org.example.exception.farm.AccessToFarmException;
import org.example.exception.farm.FarmNotFoundException;
import org.example.entity.User;
import org.example.service.FarmService;
import org.example.service.PlantService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;


@Controller
@RequestMapping("/plant")
public class PlantController {
    
    private final FarmService farmService;
    private final PlantService plantService;
    private final UserService userService;
    
    @Autowired
    public PlantController(FarmService farmService, PlantService plantService, UserService userService) {
        this.farmService = farmService;
        this.plantService = plantService;
        this.userService = userService;
    }
    
    @GetMapping("/{farm_id}/new")
    public String displayPlantCreation(Principal principal, Model model, @PathVariable("farm_id") Long farm_id) {
        try {
            User user = userService.findByLogin(principal.getName());
            model.addAttribute("farm", farmService.findFarmById(user, farm_id));
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
        return "addPlant.html";
    }
    
    @PostMapping(value = "/{farm_id}/new")
    public String createPlant(Principal principal, @Valid @ModelAttribute("plant") Plant plant, BindingResult result, @PathVariable("farm_id") Long farm_id, Model model) {
        try {
            User user = userService.findByLogin(principal.getName());
            Farm farm = farmService.findFarmById(user, farm_id);
            if (result.hasErrors()) {
                model.addAttribute("farm", farm);
                return "addPlant.html";
            } else {
                plantService.addPlant(farm, plant);
                return "redirect:/plant/{farm_id}/all";
            }
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
    }
    
    @PatchMapping(value = "/{id}")
    public String updatePlant(@Valid @ModelAttribute("plant") Plant newPlant, BindingResult result, @PathVariable("id") Long id, Model model) {
        Plant plant = plantService.findPlantById(id);
        if (result.hasErrors()) {
            model.addAttribute("farm", plant.getFarm());
            model.addAttribute("plants", plantService.findAllPlantsByFarm(plant.getFarm()));
            return "redirect:/update/plant/{id}";
        } else {
            plantService.updatePlant(id, newPlant);
            model.addAttribute("farm", plant.getFarm());
            model.addAttribute("plants", plantService.findAllPlantsByFarm(plant.getFarm()));
            return "plants.html";
        }
    }
    
    @DeleteMapping(value = "/{id}")
    public String deletePlant(@PathVariable("id") Long id, Model model) {
        Plant plant = plantService.findPlantById(id);
        Long farmId = plant.getFarm().getId();
        plantService.deletePlant(id);
        return "redirect:/plant/"+ farmId + "/all";
    }
    
    @GetMapping("/{id}/all")
    public String plants(Principal principal, Model model, @PathVariable Long id) {
        try {
            User user = userService.findByLogin(principal.getName());
            Farm farm = farmService.findFarmById(user, id);
            model.addAttribute("farm", farm);
            model.addAttribute("plants", plantService.findAllPlantsByFarm(farm));
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
        return "plants.html";
    }
}
