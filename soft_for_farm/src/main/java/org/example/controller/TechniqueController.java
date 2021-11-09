package org.example.controller;


import org.example.exception.user.UserNotFoundException;
import org.example.model.Technique;
import org.example.exception.farm.AccessToFarmException;
import org.example.exception.farm.FarmNotFoundException;
import org.example.model.User;
import org.example.service.FarmService;
import org.example.service.TechniqueService;
import org.example.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/technique")
public class TechniqueController {
    
    private final FarmService farmService;
    private final TechniqueService techniqueService;
    private final UserService userService;
    
    public TechniqueController(FarmService farmService, TechniqueService techniqueService, UserService userService) {
        this.farmService = farmService;
        this.techniqueService = techniqueService;
        this.userService = userService;
    }
    
    @GetMapping("/{farm_id}/new")
    public String displayTechniqueCreation(Principal principal, Model model, @PathVariable("farm_id") Long farm_id) {
        try {
            User user = userService.findByLogin(principal.getName());
            model.addAttribute("farm", farmService.findFarmById(user, farm_id));
        } catch (FarmNotFoundException e) {
            model.addAttribute("errorMessage", "Farm with this id not found");
            return "redirect:/farms";
        } catch (AccessToFarmException e) {
            model.addAttribute("errorMessage", "You don't have access to this farm");
            return "error.html";
        } catch (UserNotFoundException e) {
            model.addAttribute("errorMessage", "USer not found");
            return "error.html";
        }
        return "addTechnique.html";
    }
    
    @PostMapping(value = "/{farm_id}/new")
    public String createTechnique(Principal principal, @Valid @ModelAttribute("technique") Technique technique, BindingResult result, @PathVariable("farm_id") Long farm_id, Model model) {
        try {
            User user = userService.findByLogin(principal.getName());
            if (result.hasErrors()) {
                model.addAttribute("farm", farmService.findFarmById(user, farm_id));
                return "addTechnique.html";
            } else {
                techniqueService.addTechnique(farmService.findFarmById(user, farm_id), technique);
                return "redirect:/technique/{farm_id}/all";
            }
        } catch (FarmNotFoundException e) {
            model.addAttribute("errorMessage", "Farm with this id not found");
            return "redirect:/farms";
        } catch (AccessToFarmException e) {
            model.addAttribute("errorMessage", "You don't have access to this farm");
            return "error.html";
        } catch (UserNotFoundException e) {
            model.addAttribute("errorMessage", "USer not found");
            return "error.html";
        }
    }
    
    @PatchMapping(value = "/{id}")
    public String updateTechnique(@Valid @ModelAttribute("technique") Technique technique, BindingResult result, @PathVariable("id") Long id, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("farm", techniqueService.findTechniqueById(id).getFarm());
            model.addAttribute("techniques", techniqueService.findTechniqueById(id).getFarm().getTechniques());
            return "redirect:/update/techniques/{id}";
        } else {
            techniqueService.updateTechnique(id, technique);
            model.addAttribute("farm", techniqueService.findTechniqueById(id).getFarm());
            model.addAttribute("techniques", techniqueService.findTechniqueById(id).getFarm().getTechniques());
            return "techniques.html";
        }
    }
    
    @DeleteMapping(value = "/{id}")
    public String deleteTechnique(@PathVariable("id") Long id, Model model) {
        model.addAttribute("farm", techniqueService.findTechniqueById(id).getFarm());
        model.addAttribute("techniques", techniqueService.findTechniqueById(id).getFarm().getTechniques());
        techniqueService.deleteTechnique(id);
        return "techniques.html";
    }
    
    @GetMapping("/{id}/all")
    public String techniques(Principal principal, Model model, @PathVariable Long id) {
        try {
            User user = userService.findByLogin(principal.getName());
            model.addAttribute("farm", farmService.findFarmById(user, id));
            model.addAttribute("techniques", farmService.findFarmById(user, id).getTechniques());
        } catch (FarmNotFoundException e) {
            model.addAttribute("errorMessage", "Farm with this id not found");
            return "redirect:/farms";
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
