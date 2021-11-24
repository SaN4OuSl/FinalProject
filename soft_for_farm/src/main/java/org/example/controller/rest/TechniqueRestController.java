package org.example.controller.rest;

import org.example.entity.Technique;
import org.example.exception.farm.AccessToFarmException;
import org.example.exception.farm.FarmNotFoundException;
import org.example.exception.technique.AccessToTechniqueException;
import org.example.exception.technique.TechniqueNotFoundException;
import org.example.exception.user.UserNotFoundException;
import org.example.service.TechniqueService;
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
    
    private final TechniqueService techniqueService;
    
    public TechniqueRestController(TechniqueService techniqueService) {
        this.techniqueService = techniqueService;
    }
    
    @PostMapping(value = "/{farm_id}/new")
    public Object createTechnique(@Valid @RequestBody Technique technique, BindingResult result, @PathVariable Long farm_id) {
        try {
            if (result.hasErrors()) {
                return ResponseEntity.badRequest().body("Errors in fields");
            } else {
                return techniqueResponseReturner( techniqueService.addTechnique(farm_id, technique));
            }
        } catch (FarmNotFoundException | AccessToFarmException | UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PatchMapping(value = "/{id}")
    public Object updateTechnique(@Valid @RequestBody Technique newTechnique, BindingResult result, @PathVariable("id") Long id) {
        try {
            if (result.hasErrors()) {
                return ResponseEntity.badRequest().body("Errors in fields");
            } else {
                return techniqueResponseReturner(techniqueService.updateTechnique(id, newTechnique));
            }
        } catch (UserNotFoundException | TechniqueNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping(value = "/{id}")
    public Object deleteTechnique(@PathVariable("id") Long id) {
        try {
            techniqueService.deleteTechnique(id);
            return new ResponseEntity<>("Technique successfully deleted", HttpStatus.OK);
        } catch (UserNotFoundException | AccessToTechniqueException | TechniqueNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/{farm_id}/all")
    public Object techniques(@PathVariable Long farm_id) {
        try {
            return techniqueService.findAllTechniquesByFarm(farm_id);
        } catch (FarmNotFoundException | AccessToFarmException | UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public Object getTechniqueById(@PathVariable("id") Long id) {
        try {
            return techniqueResponseReturner(techniqueService.findTechniqueById(id));
        } catch (UserNotFoundException | TechniqueNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
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