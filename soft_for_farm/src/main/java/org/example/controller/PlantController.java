package org.example.controller;

import org.example.exception.user.UserNotFoundException;
import org.example.model.Plant;
import org.example.exception.farm.AccessToFarmException;
import org.example.exception.farm.FarmNotFoundException;
import org.example.model.User;
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
            if (result.hasErrors()) {
                model.addAttribute("farm", farmService.findFarmById(user, farm_id));
                return "addPlant.html";
            } else {
                plantService.addPlant(farmService.findFarmById(user, farm_id), plant);
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
    public String updatePlant(@Valid @ModelAttribute("plant") Plant plant, BindingResult result, @PathVariable("id") Long id, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("farm", plantService.findPlantById(id).getFarm());
            model.addAttribute("plants", plantService.findPlantById(id).getFarm().getPlants());
            return "redirect:/update/plant/{id}";
        } else {
            plantService.updatePlant(id, plant);
            model.addAttribute("farm", plantService.findPlantById(id).getFarm());
            model.addAttribute("plants", plantService.findPlantById(id).getFarm().getPlants());
            return "plants.html";
        }
    }
    
    @DeleteMapping(value = "/{id}")
    public String deletePlant(@PathVariable("id") Long id, Model model) {
        model.addAttribute("farm", plantService.findPlantById(id).getFarm());
        model.addAttribute("plants", plantService.findPlantById(id).getFarm().getPlants());
        plantService.deletePlant(id);
        return "plants.html";
    }
    
    @GetMapping("/{id}/all")
    public String plants(Principal principal, Model model, @PathVariable Long id) {
        try {
            User user = userService.findByLogin(principal.getName());
            model.addAttribute("farm", farmService.findFarmById(user, id));
            model.addAttribute("plants", farmService.findFarmById(user, id).getPlants());
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
