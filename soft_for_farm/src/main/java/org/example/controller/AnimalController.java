package org.example.controller;

import org.example.entity.Animal;
import org.example.entity.Farm;
import org.example.entity.User;
import org.example.exception.farm.AccessToFarmException;
import org.example.exception.farm.FarmNotFoundException;
import org.example.exception.user.UserNotFoundException;
import org.example.service.AnimalService;
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
@RequestMapping("/animal")
public class AnimalController {
    
    private final FarmService farmService;
    private final AnimalService animalService;
    private final UserService userService;
    
    @Autowired
    public AnimalController(FarmService farmService, AnimalService animalService, UserService userService) {
        this.farmService = farmService;
        this.animalService = animalService;
        this.userService = userService;
    }
    
    @GetMapping("/{farm_id}/new")
    public String displayAnimalCreation(Principal principal, Model model, @PathVariable("farm_id") Long farm_id) {
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
        return "addAnimal.html";
    }
    
    @PostMapping(value = "/{farm_id}/new")
    public String createAnimal(Principal principal, @Valid @ModelAttribute("animal") Animal animal, BindingResult result, @PathVariable("farm_id") Long farm_id, Model model) {
        try {
            User user = userService.findByLogin(principal.getName());
            Farm farm = farmService.findFarmById(user, farm_id);
            if (result.hasErrors()) {
                model.addAttribute("farm", farm);
                return "addAnimal.html";
            } else {
                animalService.addAnimal(farm, animal);
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
    public String updateAnimal(Principal principal, @Valid @ModelAttribute("animal") Animal newAnimal, BindingResult result, @PathVariable("id") Long id, Model model) {
        Animal animal = animalService.findAnimalById(id);
        try {
            User user = userService.findByLogin(principal.getName());
            Farm farm = farmService.findFarmById(user, animal.getFarm().getId());
            if (result.hasErrors()) {
                model.addAttribute("farm", farm);
                model.addAttribute("animals", animalService.findAllAnimalByFarm(farm));
                return "redirect:/update/animal/{id}";
            } else {
                animalService.updateAnimal(id, newAnimal);
                model.addAttribute("farm", farm);
                model.addAttribute("animals", animalService.findAllAnimalByFarm(farm));
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
        }
    }
    
    @DeleteMapping(value = "/{id}")
    public String deleteAnimal(Principal principal,@PathVariable("id") Long id, Model model) {
        Animal animal = animalService.findAnimalById(id);
        try {
            User user = userService.findByLogin(principal.getName());
            Farm farm = farmService.findFarmById(user, animal.getFarm().getId());
            animalService.deleteAnimal(id);
            return "redirect:/animal/" + farm.getId() + "/all";
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
    public String animals(Principal principal, Model model, @PathVariable Long id) {
        try {
            User user = userService.findByLogin(principal.getName());
            Farm farm = farmService.findFarmById(user, id);
            model.addAttribute("farm", farm);
            model.addAttribute("animals", animalService.findAllAnimalByFarm(farm));
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
