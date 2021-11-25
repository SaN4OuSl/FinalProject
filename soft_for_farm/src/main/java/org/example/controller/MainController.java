package org.example.controller;

import org.example.entity.*;
import org.example.exception.animal.AnimalNotFoundException;
import org.example.exception.farm.AccessToFarmException;
import org.example.exception.farm.FarmNotFoundException;
import org.example.exception.plant.PlantNotFoundException;
import org.example.exception.technique.TechniqueNotFoundException;
import org.example.exception.user.*;
import org.example.service.*;
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
    private final FarmService farmService;
    private final PlantService plantService;
    private final AnimalService animalService;
    private final TechniqueService techniqueService;
    
    @Autowired
    public MainController(UserService userService, FarmService farmService, PlantService plantService, AnimalService animalService, TechniqueService techniqueService) {
        this.userService = userService;
        this.farmService = farmService;
        this.plantService = plantService;
        this.animalService = animalService;
        this.techniqueService = techniqueService;
    }
    
    @DeleteMapping("/deleteYourAccount")
    public String deleteYourAccount(Principal principal, Model model) {
        try {
            User user = userService.findByLogin(principal.getName());
            userService.deleteById(user.getId());
            return "redirect:/login";
        } catch (UserNotFoundException e) {
            model.addAttribute("errorMessage", "User not found");
            return "error.html";
        } catch (NotEnoughRights notEnoughRights) {
            model.addAttribute("errorMessage", "You don't have enough rights");
            return "error.html";
        }
    }
    
    @PatchMapping("/updateYourAccount")
    public String updateYourAccount(Principal principal, @Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        try {
            if (!result.hasErrors()) {
                User currentUser = userService.findByLogin(principal.getName());
                userService.updateById(currentUser.getId(), user);
                return "redirect:/login";
            } else {
                return "redirect:/home";
            }
        } catch (UserNotFoundException e) {
            model.addAttribute("errorMessage", "User not found");
            return "error.html";
        } catch (NotEnoughRights | UserPasswordSmall | UserLoginSmall | DuplicateUserLogin e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error.html";
        }
    }
    
    @GetMapping("update/{aa}/{id}")
    public String update(Principal principal,
                         @PathVariable("aa") String action,
                         @PathVariable("id") Long id,
                         Model model, @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 5) Pageable pageable) throws UserNotFoundException, NotEnoughRights {
        User user = userService.findByLogin(principal.getName());
        Farm farm;
        int option = 0;
        if (action.equals("farms")) {
            try {
                farm = farmService.findFarmById(id);
                model.addAttribute("farm", farm);
                Page<Farm> pageFarm = farmService.findAllPageable(pageable);
                model.addAttribute("page", pageFarm);
                model.addAttribute("expense", String.format("%.2f", farmService.expensesCounter(farm)))
                        .addAttribute("profit", String.format("%.2f", farmService.profitCounter(farm)))
                        .addAttribute("netProfit", String.format("%.2f", farmService.netProfitCounter(farm)));
                model.addAttribute("currentUser", user)
                        .addAttribute("option", option);
                return "farms.html";
            } catch (FarmNotFoundException e) {
                model.addAttribute("errorMessage", "Farm with this id not found");
                return "error.html";
            } catch (AccessToFarmException e) {
                model.addAttribute("errorMessage", "You don't have access to this farm");
                return "error.html";
            }
        }
        if (action.equals("plant")) {
            try {
                Plant plant = plantService.findPlantById(id);
                Long farmId = plant.getFarm().getId();
                farm = farmService.findFarmById(farmId);
                List<Plant> plants = plantService.findAllPlantsByFarmId(farmId);
                model.addAttribute("plant", plant);
                model.addAttribute("farm", farm)
                        .addAttribute("option", option)
                        .addAttribute("plants", plants);
                model.addAttribute("expense", String.format("%.2f", plantService.expensesCounter(id)))
                        .addAttribute("profit", String.format("%.2f", plantService.profitCounter(id)))
                        .addAttribute("netProfit", String.format("%.2f", plantService.netProfitCounter(id)));
                return "plants.html";
            } catch (FarmNotFoundException e) {
                model.addAttribute("errorMessage", "Farm with this id not found");
                return "error.html";
            } catch (AccessToFarmException e) {
                model.addAttribute("errorMessage", "You don't have access to this farm");
                return "error.html";
            } catch (PlantNotFoundException e) {
                model.addAttribute("errorMessage", e.getMessage());
                return "error.html";
            }
        }
        if (action.equals("animal")) {
            try {
                Animal animal = animalService.findAnimalById(id);
                Long farmId = animal.getFarm().getId();
                farm = farmService.findFarmById(farmId);
                List<Animal> animals = animalService.findAllAnimalByFarmId(farmId);
                model.addAttribute("farm", farm)
                        .addAttribute("option", option)
                        .addAttribute("animals", animals);
                model.addAttribute("animal", animal);
                model.addAttribute("expenses", String.format("%.2f", animalService.expensesCounter(id)))
                        .addAttribute("profit", String.format("%.2f", animalService.profitCounter(id)))
                        .addAttribute("netProfit", String.format("%.2f", animalService.netProfitCounter(id)));
                return "animals.html";
            } catch (FarmNotFoundException e) {
                model.addAttribute("errorMessage", "Farm with this id not found");
                return "error.html";
            } catch (AccessToFarmException e) {
                model.addAttribute("errorMessage", "You don't have access to this farm");
                return "error.html";
            } catch (AnimalNotFoundException e) {
                model.addAttribute("errorMessage", e.getMessage());
                return "error.html";
            }
        }
        if (action.equals("technique")) {
            try {
                Technique technique = techniqueService.findTechniqueById(id);
                Long farmId = technique.getFarm().getId();
                farm = farmService.findFarmById(farmId);
                List<Technique> techniques = techniqueService.findAllTechniquesByFarm(farmId);
                model.addAttribute("technique", technique);
                model.addAttribute("expenses", String.format("%.2f", techniqueService.expensesCounter(id)));
                model.addAttribute("farm", farm)
                        .addAttribute("option", option)
                        .addAttribute("techniques", techniques);
                return "techniques.html";
            } catch (FarmNotFoundException e) {
                model.addAttribute("errorMessage", "Farm with this id not found");
                return "error.html";
            } catch (AccessToFarmException e) {
                model.addAttribute("errorMessage", "You don't have access to this farm");
                return "error.html";
            } catch (TechniqueNotFoundException e) {
                model.addAttribute("errorMessage", e.getMessage());
                return "error.html";
            }
        }
        model.addAttribute("errorMessage", "Some error");
        return "error.html";
    }
}
