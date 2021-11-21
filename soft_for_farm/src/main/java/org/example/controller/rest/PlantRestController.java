package org.example.controller.rest;

import org.example.entity.Farm;
import org.example.entity.Plant;
import org.example.entity.User;
import org.example.exception.farm.AccessToFarmException;
import org.example.exception.farm.FarmNotFoundException;
import org.example.exception.jwt.JwtTokenException;
import org.example.exception.user.UserNotFoundException;
import org.example.config.security.jwt.JwtTokenProvider;
import org.example.service.FarmService;
import org.example.service.PlantService;
import org.example.service.UserService;
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
    
    private final FarmService farmService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PlantService plantService;
    private final UserService userService;
    
    public PlantRestController(FarmService farmService, JwtTokenProvider jwtTokenProvider, PlantService plantService, UserService userService) {
        this.farmService = farmService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.plantService = plantService;
        this.userService = userService;
    }
    
    @PostMapping(value = "/{farm_id}/new")
    public Object createPlant(String token, @Valid @RequestBody Plant plant, BindingResult result, @PathVariable Long farm_id) {
        try {
            User user = userService.findByLogin(jwtTokenProvider.getLogin(token));
            Farm farm = farmService.findFarmById(user, farm_id);
            if (result.hasErrors()) {
                return ResponseEntity.badRequest().body("Errors in fields");
            } else {
                plantService.addPlant(farm, plant);
                return plantResponseReturner(plant);
            }
        } catch (FarmNotFoundException | AccessToFarmException | UserNotFoundException | JwtTokenException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PatchMapping(value = "/{id}")
    public Object updatePlant(String token, @Valid @RequestBody Plant newPlant, BindingResult result, @PathVariable("id") Long id) {
        Plant plant = plantService.findPlantById(id);
        if (plant != null) {
            try {
                User user = userService.findByLogin(jwtTokenProvider.getLogin(token));
                if (plantService.checkAccessToPlant(user, id)) {
                    if (result.hasErrors()) {
                        return ResponseEntity.badRequest().body("Errors in fields");
                    } else {
                        plantService.updatePlant(id, newPlant);
                        return plantResponseReturner(plant);
                    }
                } else {
                    return ResponseEntity.status(403).body("You dont have access");
                }
            } catch (UserNotFoundException | JwtTokenException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        return ResponseEntity.badRequest().body("Plant not found");
    }
    
    @DeleteMapping(value = "/{id}")
    public Object deletePlant(String token, @PathVariable("id") Long id) {
        Plant plant = plantService.findPlantById(id);
        if (plant != null) {
            try {
                User user = userService.findByLogin(jwtTokenProvider.getLogin(token));
                if (plantService.checkAccessToPlant(user, id)) {
                    plantService.deletePlant(id);
                    return new ResponseEntity<>("Plant successfully deleted", HttpStatus.OK);
                } else {
                    return ResponseEntity.status(403).body("You dont have access");
                }
            } catch (UserNotFoundException | JwtTokenException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        } else {
            return ResponseEntity.badRequest().body("Plant not found");
        }
    }
    
    @GetMapping("/{farm_id}/all")
    public Object plants(String token, @PathVariable Long farm_id) {
        try {
            User user = userService.findByLogin(jwtTokenProvider.getLogin(token));
            Farm farm = farmService.findFarmById(user, farm_id);
            return plantService.findAllPlantsByFarm(farm);
        } catch (FarmNotFoundException | AccessToFarmException | UserNotFoundException | JwtTokenException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public Object getPlantById(String token, @PathVariable("id") Long id) {
        Plant plant = plantService.findPlantById(id);
        if (plant != null) {
            try {
                User user = userService.findByLogin(jwtTokenProvider.getLogin(token));
                if (plantService.checkAccessToPlant(user, id)) {
                    return plantResponseReturner(plant);
                } else {
                    return ResponseEntity.status(403).body("You dont have access");
                }
            } catch (UserNotFoundException | JwtTokenException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        } else {
            return ResponseEntity.badRequest().body("Plant not found");
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