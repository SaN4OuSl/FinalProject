package org.example.controller;

import org.example.entity.Animal;
import org.example.entity.Farm;
import org.example.exception.animal.AccessToAnimalException;
import org.example.exception.animal.AnimalNotFoundException;
import org.example.exception.farm.AccessToFarmException;
import org.example.exception.farm.FarmNotFoundException;
import org.example.exception.user.UserNotFoundException;
import org.example.service.AnimalService;
import org.example.service.FarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public String displayAnimalForm(Model model, @PathVariable("farm_id") Long farm_id) {
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
        return "addAnimal.html";
    }
    
    @PostMapping(value = "/{farm_id}/new")
    public String createAnimal(@Valid @ModelAttribute("animal") Animal animal, BindingResult result, @PathVariable("farm_id") Long farmId, Model model) {
        try {
            if (result.hasErrors()) {
                model.addAttribute("farm", farmService.findFarmById(farmId));
                return "addAnimal.html";
            } else {
                animalService.addAnimal(farmId, animal);
                return "redirect:/animal/{farm_id}/all";
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
    public String updateAnimal(@Valid @ModelAttribute("animal") Animal newAnimal, BindingResult result, @PathVariable("id") Long id, Model model) {
        try {
            Farm farm = animalService.getFarmByAnimalId(id);
            if (result.hasErrors()) {
                model.addAttribute("farm", farm);
                model.addAttribute("animals", animalService.findAllAnimalByFarmId(farm.getId()));
                return "redirect:/update/animal/{id}";
            } else {
                animalService.updateAnimal(id, newAnimal);
                model.addAttribute("farm", farm);
                model.addAttribute("animals", animalService.findAllAnimalByFarmId(farm.getId()));
                return "animals.html";
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
        } catch (AnimalNotFoundException e) {
            model.addAttribute("errorMessage", "Animal with this id not found");
            return "error.html";
        }
    }
    
    @DeleteMapping(value = "/{id}")
    public String deleteAnimal(@PathVariable("id") Long id, Model model) {
        try {
            Animal animal = animalService.findAnimalById(id);
            animalService.deleteAnimal(id);
            return "redirect:/animal/" + animal.getFarm().getId() + "/all";
        } catch (UserNotFoundException e) {
            model.addAttribute("errorMessage", "User not found");
            return "error.html";
        } catch (AnimalNotFoundException | AccessToAnimalException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error.html";
        }
    }
    
    @GetMapping("/{id}/all")
    public String animals(Model model, @PathVariable Long id) {
        try {
            model.addAttribute("farm", farmService.findFarmById(id));
            model.addAttribute("animals", animalService.findAllAnimalByFarmId(id));
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
        
        return "animals.html";
    }
}
