package org.example.controller;

import org.example.entity.Plant;
import org.example.service.FarmService;
import org.example.service.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


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
    public String displayPlantCreation(Model model, @PathVariable("farm_id") Long farm_id) {
        model.addAttribute("farm", farmService.findFarmById(farm_id));
        return "addPlant.html";
    }
    
    @PostMapping(value = "/{farm_id}/new")
    public String createPlant(@Valid @ModelAttribute("plant") Plant plant, BindingResult result, @PathVariable("farm_id") Long farm_id, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("farm", farmService.findFarmById(farm_id));
            return "addPlant.html";
        } else {
            plantService.addPlant(farmService.findFarmById(farm_id), plant);
            return "redirect:/plant/{farm_id}/all";
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
    public String plants(Model model, @PathVariable Long id) {
        model.addAttribute("farm", farmService.findFarmById(id));
        model.addAttribute("plants", farmService.findFarmById(id).getPlants());
        return "plants.html";
    }
}
