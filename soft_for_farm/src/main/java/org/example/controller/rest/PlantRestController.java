package org.example.controller.rest;

import org.example.entity.Plant;
import org.example.exception.farm.AccessToFarmException;
import org.example.exception.farm.FarmNotFoundException;
import org.example.exception.plant.AccessToPlantException;
import org.example.exception.plant.PlantNotFoundException;
import org.example.exception.user.UserNotFoundException;
import org.example.service.PlantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/plant")
public class PlantRestController {
    
    private final PlantService plantService;
    
    public PlantRestController(PlantService plantService) {
        this.plantService = plantService;
    }
    
    @PostMapping(value = "/{farm_id}/new")
    public Object createPlant(@Valid @RequestBody Plant plant, BindingResult result, @PathVariable Long farm_id) {
        try {
            if (result.hasErrors()) {
                return ResponseEntity.badRequest().body("Errors in fields");
            } else {
                return plantResponseReturner(plantService.addPlant(farm_id, plant));
            }
        } catch (FarmNotFoundException | AccessToFarmException | UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PatchMapping(value = "/{id}")
    public Object updatePlant(@Valid @RequestBody Plant newPlant, BindingResult result, @PathVariable("id") Long id) {
        try {
            if (result.hasErrors()) {
                return ResponseEntity.badRequest().body("Errors in fields");
            } else {
                return plantResponseReturner(plantService.updatePlant(id, newPlant));
            }
        } catch (UserNotFoundException | PlantNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping(value = "/{id}")
    public Object deletePlant(@PathVariable("id") Long id) {
        try {
            plantService.deletePlant(id);
            return new ResponseEntity<>("Plant successfully deleted", HttpStatus.OK);
        } catch (UserNotFoundException | PlantNotFoundException | AccessToPlantException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/{farm_id}/all")
    public Object plants(@PathVariable Long farm_id) {
        try {
            return plantService.findAllPlantsByFarmId(farm_id);
        } catch (FarmNotFoundException | AccessToFarmException | UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public Object getPlantById(@PathVariable("id") Long id) {
        try {
            return plantResponseReturner(plantService.findPlantById(id));
        } catch (UserNotFoundException | PlantNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    private Object plantResponseReturner(Plant plant) {
        Map<String, String> response = new HashMap<>();
        response.put("Id", String.valueOf(plant.getId()));
        response.put("Plant name", plant.getPlantName());
        response.put("Cost of plant", String.valueOf(plant.getCostOfPlant()));
        response.put("Plant harvest", String.valueOf(plant.getPlantHarvest()));
        response.put("Other expense", String.valueOf(plant.getOtherExpense()));
        response.put("Cost of fertilizers", String.valueOf(plant.getCostOfFertilizers()));
        response.put("Rental price of fields", String.valueOf(plant.getRentalPriceOfField()));
        response.put("Size pf field for plant", String.valueOf(plant.getSizeOfFieldForPlant()));
        response.put("Profit", String.valueOf(plantService.profitCounter(plant.getId())));
        response.put("Expense", String.valueOf(plantService.expensesCounter(plant.getId())));
        response.put("Net profit", String.valueOf(plantService.netProfitCounter(plant.getId())));
        return ResponseEntity.ok(response);
    }
}