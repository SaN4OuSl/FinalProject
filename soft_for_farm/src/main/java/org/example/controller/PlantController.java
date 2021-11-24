package org.example.controller;

import org.example.entity.Farm;
import org.example.entity.Plant;
import org.example.exception.farm.AccessToFarmException;
import org.example.exception.farm.FarmNotFoundException;
import org.example.exception.user.UserNotFoundException;
import org.example.service.FarmService;
import org.example.service.PlantService;
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
    
    @Autowired
    public PlantController(FarmService farmService, PlantService plantService) {
        this.farmService = farmService;
        this.plantService = plantService;
    }
    
    @GetMapping("/{farm_id}/new")
    public String displayPlantForm(Model model, @PathVariable("farm_id") Long farm_id) {
        try {
            model.addAttribute("farm", farmService.findFarmById(farm_id));
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
    public String createPlant(@Valid @ModelAttribute("plant") Plant plant, BindingResult result, @PathVariable("farm_id") Long farm_id, Model model) {
        try {
            Farm farm = farmService.findFarmById(farm_id);
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
        try {
            Farm farm = farmService.findFarmById(plant.getFarm().getId());
            if (result.hasErrors()) {
                model.addAttribute("farm", farm);
                model.addAttribute("plants", plantService.findAllPlantsByFarm(farm));
                return "redirect:/update/plant/{id}";
            } else {
                plantService.updatePlant(id, newPlant);
                model.addAttribute("farm", farm);
                model.addAttribute("plants", plantService.findAllPlantsByFarm(farm));
                return "plants.html";
            }
        } catch (UserNotFoundException e) {
            model.addAttribute("errorMessage", "User not found");
            return "error.html";
        } catch (FarmNotFoundException e) {
            model.addAttribute("errorMessage", "Farm with this id not found");
            return "error.html";
        } catch (AccessToFarmException e) {
            model.addAttribute("errorMessage", "You don't have access to this farm");
            return "error.html";
        }
    }
    
    @DeleteMapping(value = "/{id}")
    public String deletePlant(Principal principal, @PathVariable("id") Long id, Model model) {
        Plant plant = plantService.findPlantById(id);
        try {
            Farm farm = farmService.findFarmById(plant.getFarm().getId());
            plantService.deletePlant(id);
            return "redirect:/plant/" + farm.getId() + "/all";
        } catch (UserNotFoundException e) {
            model.addAttribute("errorMessage", "User not found");
            return "error.html";
        } catch (FarmNotFoundException e) {
            model.addAttribute("errorMessage", "Farm with this id not found");
            return "error.html";
        } catch (AccessToFarmException e) {
            model.addAttribute("errorMessage", "You don't have access to this farm");
            return "error.html";
        }
    }
    
    @GetMapping("/{id}/all")
    public String plants(Model model, @PathVariable Long id) {
        try {
            Farm farm = farmService.findFarmById(id);
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
