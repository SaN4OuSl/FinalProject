package org.example.controller;

import org.example.entity.Farm;
import org.example.entity.auth.User;
import org.example.exception.UserNotFoundException;
import org.example.service.FarmService;
import org.example.service.FieldService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;

@Controller
public class MainController {
    
    private final UserService userService;
    private final FarmService farmService;
    private final FieldService fieldService;
    
    @Autowired
    public MainController(UserService userService, FarmService farmService, FieldService fieldService) {
        this.userService = userService;
        this.farmService = farmService;
        this.fieldService = fieldService;
    }
    
    @GetMapping("/farms")
    public String farms(Principal principal, Model model) throws UserNotFoundException {
        User user = userService.findByLogin(principal.getName());
        model.addAttribute("farms", user.getFarms());
        model.addAttribute("currentUser", user);
        return "farms.html";
    }
    
    @GetMapping("update/{aa}/{id}")
    public String update(Principal principal,
                         @PathVariable("aa") String action,
                         @PathVariable("id") Long id,
                         Model model) throws UserNotFoundException {
        User user = userService.findByLogin(principal.getName());
        List<Farm> farms = user.getFarms();
        int option = 0;
        if (action.equals("farms")) {
            model.addAttribute("farm", farmService.findFarmById(id));
            model.addAttribute("currentUser", user)
                    .addAttribute("option", option)
                    .addAttribute("farms", farms);
            return "farms.html";
        }
        if (action.equals("field")) {
            model.addAttribute("field", fieldService.findFieldById(id));
            model.addAttribute("currentFarm", fieldService.findFieldById(id).getFarm())
                    .addAttribute("option", option)
                    .addAttribute("fields", fieldService.findFieldById(id).getFarm().getFields());
            return "fields.html";
        }
        return "farms.html";
    }
    
    @GetMapping("/update")
    public String update(Principal principal,
                         @ModelAttribute("farm") Farm farm,
                         Model model) throws UserNotFoundException {
        
        User user = userService.findByLogin(principal.getName());
        List<Farm> farms = user.getFarms();
        model.addAttribute("currentUser", user)
                .addAttribute("farms", farm);
        return "farms.html";
        
    }
}
