package org.example.controller.rest;

import org.example.entity.Animal;
import org.example.exception.animal.AccessToAnimalException;
import org.example.exception.animal.AnimalNotFoundException;
import org.example.exception.farm.AccessToFarmException;
import org.example.exception.farm.FarmNotFoundException;
import org.example.exception.user.UserNotFoundException;
import org.example.service.AnimalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/animal")
public class AnimalRestController {
    
    private final AnimalService animalService;
    
    public AnimalRestController(AnimalService animalService) {
        this.animalService = animalService;
    }
    
    @PostMapping(value = "/{farmId}/new")
    public Object createAnimal(@Valid @RequestBody Animal animal, BindingResult result, @PathVariable Long farmId) {
        try {
            if (result.hasErrors()) {
                return ResponseEntity.badRequest().body("Errors in fields");
            } else {
                return animalResponseReturner(animalService.addAnimal(farmId, animal));
            }
        } catch (FarmNotFoundException | AccessToFarmException | UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PatchMapping(value = "/{id}")
    public Object updateAnimal(@Valid @RequestBody Animal newAnimal, BindingResult result, @PathVariable("id") Long id) {
        try {
            if (result.hasErrors()) {
                return ResponseEntity.badRequest().body("Errors in fields");
            } else {
                return animalResponseReturner(animalService.updateAnimal(id, newAnimal));
            }
        } catch (UserNotFoundException | AnimalNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping(value = "/{id}")
    public Object deleteAnimal(@PathVariable("id") Long id) {
        try {
            animalService.deleteAnimal(id);
            return new ResponseEntity<>("Animal successfully deleted", HttpStatus.OK);
        } catch (UserNotFoundException | AccessToAnimalException | AnimalNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/{farm_id}/all")
    public Object animals(@PathVariable Long farm_id) {
        try {
            return animalService.findAllAnimalByFarmId(farm_id);
        } catch (FarmNotFoundException | AccessToFarmException | UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public Object getAnimalById(@PathVariable("id") Long id) {
        try {
            return animalService.findAnimalById(id);
        } catch (UserNotFoundException | AnimalNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    private Object animalResponseReturner(Animal animal) {
        Map<String, String> response = new HashMap<>();
        response.put("Id", String.valueOf(animal.getId()));
        response.put("Animal name", animal.getAnimalName());
        response.put("Cost of one animal", String.valueOf(animal.getCostOfOneAnimal()));
        response.put("Number of animals", String.valueOf(animal.getNumberOfAnimals()));
        response.put("Other expense", String.valueOf(animal.getOtherExpenses()));
        response.put("Cost of feeds", String.valueOf(animal.getCostOfFeeds()));
        response.put("Rental price of buildings", String.valueOf(animal.getRentalPriceOfBuilding()));
        response.put("Profit", String.valueOf(animalService.profitCounter(animal.getId())));
        response.put("Expense", String.valueOf(animalService.expensesCounter(animal.getId())));
        response.put("Net profit", String.valueOf(animalService.netProfitCounter(animal.getId())));
        return ResponseEntity.ok(response);
    }
}