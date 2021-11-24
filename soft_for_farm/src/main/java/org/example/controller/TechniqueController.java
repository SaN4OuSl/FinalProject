package org.example.controller;


import org.example.entity.Farm;
import org.example.entity.Technique;
import org.example.exception.farm.AccessToFarmException;
import org.example.exception.farm.FarmNotFoundException;
import org.example.exception.user.UserNotFoundException;
import org.example.service.FarmService;
import org.example.service.TechniqueService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/technique")
public class TechniqueController {
    
    private final FarmService farmService;
    private final TechniqueService techniqueService;
    
    public TechniqueController(FarmService farmService, TechniqueService techniqueService) {
        this.farmService = farmService;
        this.techniqueService = techniqueService;
    }
    
    @GetMapping("/{farm_id}/new")
    public String displayTechniqueForm(Model model, @PathVariable("farm_id") Long farm_id) {
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
        return "addTechnique.html";
    }
    
    @PostMapping(value = "/{farm_id}/new")
    public String createTechnique(@Valid @ModelAttribute("technique") Technique technique, BindingResult result, @PathVariable("farm_id") Long farm_id, Model model) {
        try {
            Farm farm = farmService.findFarmById(farm_id);
            if (result.hasErrors()) {
                model.addAttribute("farm", farm);
                return "addTechnique.html";
            } else {
                techniqueService.addTechnique(farm, technique);
                return "redirect:/technique/{farm_id}/all";
            }
        } catch (FarmNotFoundException e) {
            model.addAttribute("errorMessage", "Farm with this id not found");
            return "redirect:/farm";
        } catch (AccessToFarmException e) {
            model.addAttribute("errorMessage", "You don't have access to this farm");
            return "error.html";
        } catch (UserNotFoundException e) {
            model.addAttribute("errorMessage", "USer not found");
            return "error.html";
        }
    }
    
    @PatchMapping(value = "/{id}")
    public String updateTechnique(@Valid @ModelAttribute("technique") Technique newTechnique, BindingResult result, @PathVariable("id") Long id, Model model) {
        Technique technique = techniqueService.findTechniqueById(id);
        try {
            Farm farm = farmService.findFarmById(technique.getFarm().getId());
            if (result.hasErrors()) {
                model.addAttribute("farm", farm);
                model.addAttribute("techniques", techniqueService.findAllTechniquesByFarm(farm));
                return "redirect:/update/techniques/{id}";
            } else {
                techniqueService.updateTechnique(id, newTechnique);
                model.addAttribute("farm", farm);
                model.addAttribute("techniques", techniqueService.findAllTechniquesByFarm(farm));
                return "techniques.html";
            }
        } catch (FarmNotFoundException e) {
            model.addAttribute("errorMessage", "Farm with this id not found");
            return "redirect:/farm";
        } catch (AccessToFarmException e) {
            model.addAttribute("errorMessage", "You don't have access to this farm");
            return "error.html";
        } catch (UserNotFoundException e) {
            model.addAttribute("errorMessage", "USer not found");
            return "error.html";
        }
    }
    
    @DeleteMapping(value = "/{id}")
    public String deleteTechnique(@PathVariable("id") Long id, Model model) {
        Technique technique = techniqueService.findTechniqueById(id);
        try {
            Farm farm = farmService.findFarmById(technique.getFarm().getId());
            techniqueService.deleteTechnique(id);
            return "redirect:/technique/" + farm.getId() + "/all";
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
    public String techniques( Model model, @PathVariable Long id) {
        try {
            Farm farm = farmService.findFarmById(id);
            model.addAttribute("farm", farm);
            model.addAttribute("techniques", techniqueService.findAllTechniquesByFarm(farm));
        } catch (FarmNotFoundException e) {
            model.addAttribute("errorMessage", "Farm with this id not found");
            return "redirect:/farm";
        } catch (AccessToFarmException e) {
            model.addAttribute("errorMessage", "You don't have access to this farm");
            return "error.html";
        } catch (UserNotFoundException e) {
            model.addAttribute("errorMessage", "USer not found");
            return "error.html";
        }
        return "techniques.html";
    }
}
