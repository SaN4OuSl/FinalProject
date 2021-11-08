package org.example.controller;

import org.example.entity.Animal;
import org.example.exception.farm.AccessToFarmException;
import org.example.exception.farm.FarmNotFoundException;
import org.example.service.AnimalService;
import org.example.service.FarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/animal")
public class AnimalController {
    
    private final FarmService farmService;
    private final AnimalService animalService;
    
    @Autowired
    public AnimalController(FarmService farmService, AnimalService animalService) {
        this.farmService = farmService;
        this.animalService = animalService;
    }
    
    @GetMapping("/{farm_id}/new")
    public String displayAnimalCreation(Principal principal, Model model, @PathVariable("farm_id") Long farm_id) {
        try {
            model.addAttribute("farm", farmService.findFarmById(principal, farm_id));
        } catch (FarmNotFoundException e) {
            model.addAttribute("errorMessage", "Farm with this id not found");
            return "error.html";
        } catch (AccessToFarmException e) {
            model.addAttribute("errorMessage", "You don't have access to this farm");
            return "error.html";
        }
        return "addAnimal.html";
    }
    
    @PostMapping(value = "/{farm_id}/new")
    public String createAnimal(Principal principal, @Valid @ModelAttribute("animal") Animal animal, BindingResult result, @PathVariable("farm_id") Long farm_id, Model model) {
        try {
            if (result.hasErrors()) {
                model.addAttribute("farm", farmService.findFarmById(principal, farm_id));
                return "addAnimal.html";
            } else {
                animalService.addAnimal(farmService.findFarmById(principal, farm_id), animal);
                return "redirect:/animal/{farm_id}/all";
            }
        } catch (FarmNotFoundException e) {
            model.addAttribute("errorMessage", "Farm with this id not found");
            return "error.html";
        } catch (AccessToFarmException e) {
            model.addAttribute("errorMessage", "You don't have access to this farm");
            return "error.html";
        }
    }
    
    @PatchMapping(value = "/{id}")
    public String updateAnimal(Principal principal, @Valid @ModelAttribute("animal") Animal animal, BindingResult result, @PathVariable("id") Long id, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("farm", animalService.findAnimalById(id).getFarm());
            model.addAttribute("animals", animalService.findAnimalById(id).getFarm().getAnimals());
            return "redirect:/update/animal/{id}";
        } else {
            animalService.updateAnimal(id, animal);
            model.addAttribute("farm", animalService.findAnimalById(id).getFarm());
            model.addAttribute("animals", animalService.findAnimalById(id).getFarm().getAnimals());
            return "animals.html";
        }
    }
    
    @DeleteMapping(value = "/{id}")
    public String deleteAnimal(@PathVariable("id") Long id, Model model) {
        model.addAttribute("farm", animalService.findAnimalById(id).getFarm());
        model.addAttribute("animals", animalService.findAnimalById(id).getFarm().getAnimals());
        animalService.deleteAnimal(id);
        return "animals.html";
    }
    
    @GetMapping("/{id}/all")
    public String animals(Principal principal, Model model, @PathVariable Long id) {
        try {
            model.addAttribute("farm", farmService.findFarmById(principal, id));
            model.addAttribute("animals", farmService.findFarmById(principal, id).getAnimals());
        } catch (FarmNotFoundException e) {
            model.addAttribute("errorMessage", "Farm with this id not found");
            return "error.html";
        } catch (AccessToFarmException e) {
            model.addAttribute("errorMessage", "You don't have access to this farm");
            return "error.html";
        }
        
        return "animals.html";
    }
}
