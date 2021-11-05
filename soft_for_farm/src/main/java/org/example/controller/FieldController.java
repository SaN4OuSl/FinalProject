package org.example.controller;

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
    
    @GetMapping("/{farm_id}/new")
    public String displayFieldCreation(Model model, @PathVariable("farm_id") Long farm_id) {
        model.addAttribute("farm", farmService.findFarmById(farm_id));
        return "addField.html";
    }
    
    @PostMapping(value = "/{farm_id}/new")
    public String createField(Field field, BindingResult result, @PathVariable("farm_id") Long farm_id) {
        if (result.hasErrors()) {
            return "addField.html";
        } else {
            fieldService.addField(farmService.findFarmById(farm_id), field);
            return "redirect:/field/{farm_id}/all";
        }
    }
    
    @PatchMapping(value = "/{id}")
    public String updateField(Field field, @PathVariable("id") Long id, BindingResult result) {
        if (!result.hasErrors()) {
            fieldService.updateField(id, field);
        }
        return "redirect:/field/{" + field.getFarm().getId() + "}/all";
    }
    
    @DeleteMapping(value = "/{farm_id}/{id}")
    public String deleteField(@PathVariable("id") Long id, @PathVariable Long farm_id) {
        fieldService.deleteField(farmService.findFarmById(farm_id), id);
        return "redirect:/field/{" + farmService.findFarmById(farm_id) + "}/all";
    }
    
    @GetMapping("/{id}/all")
    public String fields(Model model, @PathVariable Long id) {
        model.addAttribute("farm", farmService.findFarmById(id));
        model.addAttribute("fields", farmService.findFarmById(id).getFields());
        model.addAttribute("currentUser", farmService.findFarmById(id).getUser().getLogin());
        return "fields.html";
    }
}
