package org.example.controller;

import org.example.entity.Farm;
import org.example.entity.plant.Field;
import org.example.service.FarmService;
import org.example.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/field")
public class FieldController {
    
    private final FarmService farmService;
    private final FieldService fieldService;
    
    @Autowired
    public FieldController(FarmService farmService, FieldService fieldService) {
        this.farmService = farmService;
        this.fieldService = fieldService;
    }
    
    @GetMapping("/{id}/new")
    public String displayFieldCreation(Farm farm, Model model) {
        model.addAttribute("farm", farmService.findFarmById(farm.getId()));
        return "addField.html";
    }
    
    @PostMapping(value = "/{id}/new")
    public String createField(Farm farm, Field field, BindingResult result) {
        if (result.hasErrors()) {
            return "addField.html";
        } else {
            fieldService.addField(farm, field);
            return "redirect:/field/{id}/all";
        }
    }
    
    @PatchMapping(value = "/{id}")
    public String updateField(Field field, @PathVariable("id") Long id, BindingResult result) {
        if (!result.hasErrors()) {
            fieldService.updateField(id, field);
        }
        return "redirect:/field/{"+ field.getFarm().getId()+ "}/all";
    }
    
    @DeleteMapping(value = "/{id}")
    public String deleteField(Farm farm, @PathVariable("id") Long id) {
        fieldService.deleteField(farm, id);
        return "redirect:/field/{"+ farm.getId()+ "}/all";
    }
    
    @GetMapping("/{id}/all")
    public String fields(Farm farm, Model model) {
        model.addAttribute("farm", farmService.findFarmById(farm.getId()));
        model.addAttribute("fields", farmService.findFarmById(farm.getId()).getFields());
        model.addAttribute("currentUser", farmService.findFarmById(farm.getId()).getUser().getLogin());
        return "fields.html";
    }
}
