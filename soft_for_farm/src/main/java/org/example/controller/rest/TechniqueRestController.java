package org.example.controller.rest;

import org.example.entity.Farm;
import org.example.entity.Technique;
import org.example.entity.User;
import org.example.exception.farm.AccessToFarmException;
import org.example.exception.farm.FarmNotFoundException;
import org.example.exception.user.UserNotFoundException;
import org.example.service.FarmService;
import org.example.service.TechniqueService;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/technique")
public class TechniqueRestController {
    
    private final FarmService farmService;
    private final TechniqueService techniqueService;
    private final UserService userService;
    
    public TechniqueRestController(FarmService farmService, TechniqueService techniqueService, UserService userService) {
        this.farmService = farmService;
        this.techniqueService = techniqueService;
        this.userService = userService;
    }
    
    @PostMapping(value = "/{farm_id}/new")
    public Object createTechnique(@Valid @RequestBody Technique technique, BindingResult result, @PathVariable Long farm_id) {
        try {
            Farm farm = farmService.findFarmById(farm_id);
            if (result.hasErrors()) {
                return ResponseEntity.badRequest().body("Errors in fields");
            } else {
                techniqueService.addTechnique(farm, technique);
                return techniqueResponseReturner(technique);
            }
        } catch (FarmNotFoundException | AccessToFarmException | UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PatchMapping(value = "/{id}")
    public Object updateTechnique(@Valid @RequestBody Technique newTechnique, BindingResult result, @PathVariable("id") Long id) {
        Technique technique = techniqueService.findTechniqueById(id);
        if (technique != null) {
            try {
                User user = userService.getUserByAuthentication();
                if (techniqueService.checkAccessToTechnique(user, id)) {
                    if (result.hasErrors()) {
                        return ResponseEntity.badRequest().body("Errors in fields");
                    } else {
                        techniqueService.updateTechnique(id, newTechnique);
                        return techniqueResponseReturner(technique);
                    }
                } else {
                    return ResponseEntity.status(403).body("You dont have access");
                }
            } catch (UserNotFoundException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        return ResponseEntity.badRequest().body("Technique not found");
    }
    
    @DeleteMapping(value = "/{id}")
    public Object deleteTechnique(@PathVariable("id") Long id) {
        Technique technique = techniqueService.findTechniqueById(id);
        if (technique != null) {
            try {
                User user = userService.getUserByAuthentication();
                if (techniqueService.checkAccessToTechnique(user, id)) {
                    techniqueService.deleteTechnique(id);
                    return new ResponseEntity<>("Technique successfully deleted", HttpStatus.OK);
                } else {
                    return ResponseEntity.status(403).body("You dont have access");
                }
            } catch (UserNotFoundException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        } else {
            return ResponseEntity.badRequest().body("Technique not found");
        }
    }
    
    @GetMapping("/{farm_id}/all")
    public Object techniques(@PathVariable Long farm_id) {
        try {
            Farm farm = farmService.findFarmById(farm_id);
            return techniqueService.findAllTechniquesByFarm(farm);
        } catch (FarmNotFoundException | AccessToFarmException | UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public Object getTechniqueById(@PathVariable("id") Long id) {
        Technique technique = techniqueService.findTechniqueById(id);
        if (technique != null) {
            try {
                User user = userService.getUserByAuthentication();
                if (techniqueService.checkAccessToTechnique(user, id)) {
                    return techniqueResponseReturner(technique);
                } else {
                    return ResponseEntity.status(403).body("You dont have access");
                }
            } catch (UserNotFoundException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        } else {
            return ResponseEntity.badRequest().body("Technique not found");
        }
    }
    
    private Object techniqueResponseReturner(Technique technique) {
        Map<String, String> response = new HashMap<>();
        response.put("Id", String.valueOf(technique.getId()));
        response.put("Technique type", technique.getTypeOfTechnique());
        response.put("Price of parts", String.valueOf(technique.getPriceOfParts()));
        response.put("Price of lubricants", String.valueOf(technique.getPriceOfLubricant()));
        response.put("Expense", String.valueOf(techniqueService.expensesCounter(technique.getId())));
        return ResponseEntity.ok(response);
    }
}