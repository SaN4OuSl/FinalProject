package org.example.controller;

import org.example.entity.plant.Field;
import org.example.service.FarmService;
import org.example.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


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
    
    @GetMapping("/{farm_id}/new")
    public String displayFieldCreation(Model model, @PathVariable("farm_id") Long farm_id) {
        model.addAttribute("farm", farmService.findFarmById(farm_id));
        return "addField.html";
    }
    
    @PostMapping(value = "/{farm_id}/new")
    public String createField(@Valid @ModelAttribute("field") Field field, BindingResult result, @PathVariable("farm_id") Long farm_id, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("farm", farmService.findFarmById(farm_id));
            return "addField.html";
        } else {
            fieldService.addField(farmService.findFarmById(farm_id), field);
            return "redirect:/field/{farm_id}/all";
        }
    }
    
    @PatchMapping(value = "/{id}")
    public String updateField(@Valid @ModelAttribute("field") Field field, BindingResult result, @PathVariable("id") Long id, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("farm", fieldService.findFieldById(id).getFarm());
            model.addAttribute("fields", fieldService.findFieldById(id).getFarm().getFields());
            return "redirect:/update/field/{id}";
        } else {
            fieldService.updateField(id, field);
            model.addAttribute("farm", fieldService.findFieldById(id).getFarm());
            model.addAttribute("fields", fieldService.findFieldById(id).getFarm().getFields());
            return "fields.html";
        }
    }
    
    @DeleteMapping(value = "/{id}")
    public String deleteField(@PathVariable("id") Long id, Model model) {
        model.addAttribute("farm", fieldService.findFieldById(id).getFarm());
        model.addAttribute("fields", fieldService.findFieldById(id).getFarm().getFields());
        fieldService.deleteField(id);
        return "fields.html";
    }
    
    @GetMapping("/{id}/all")
    public String fields(Model model, @PathVariable Long id) {
        model.addAttribute("farm", farmService.findFarmById(id));
        model.addAttribute("fields", farmService.findFarmById(id).getFields());
        return "fields.html";
    }
}
