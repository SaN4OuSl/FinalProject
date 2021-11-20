package org.example.controller.rest;

import org.example.entity.Animal;
import org.example.entity.Farm;
import org.example.entity.User;
import org.example.exception.farm.AccessToFarmException;
import org.example.exception.farm.FarmNotFoundException;
import org.example.exception.user.UserNotFoundException;
import org.example.security.jwt.JwtTokenProvider;
import org.example.service.AnimalService;
import org.example.service.FarmService;
import org.example.service.UserService;
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
    
    private final FarmService farmService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AnimalService animalService;
    private final UserService userService;
    
    public AnimalRestController(FarmService farmService, JwtTokenProvider jwtTokenProvider, AnimalService animalService, UserService userService) {
        this.farmService = farmService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.animalService = animalService;
        this.userService = userService;
    }
    
    @PostMapping(value = "/{farm_id}/new")
    public Object createAnimal(String token, @Valid @RequestBody Animal animal, BindingResult result, @PathVariable Long farm_id) {
        try {
            User user = userService.findByLogin(jwtTokenProvider.getLogin(token));
            Farm farm = farmService.findFarmById(user, farm_id);
            if (result.hasErrors()) {
                return ResponseEntity.badRequest().body("Errors in fields");
            } else {
                animalService.addAnimal(farm, animal);
                return animalResponseReturner(animal);
            }
        } catch (FarmNotFoundException | AccessToFarmException | UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PatchMapping(value = "/{id}")
    public Object updateAnimal(String token, @Valid @RequestBody Animal newAnimal, BindingResult result, @PathVariable("id") Long id) {
        Animal animal = animalService.findAnimalById(id);
        if (animal != null) {
            try {
                User user = userService.findByLogin(jwtTokenProvider.getLogin(token));
                if (animalService.checkAccessToAnimal(user, id)) {
                    if (result.hasErrors()) {
                        return ResponseEntity.badRequest().body("Errors in fields");
                    } else {
                        animalService.updateAnimal(id, newAnimal);
                        return animalResponseReturner(animal);
                    }
                } else {
                    return ResponseEntity.status(403).body("You dont have access");
                }
            } catch (UserNotFoundException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        return ResponseEntity.badRequest().body("Animal not found");
    }
    
    @DeleteMapping(value = "/{id}")
    public Object deleteAnimal(String token, @PathVariable("id") Long id) {
        Animal animal = animalService.findAnimalById(id);
        if (animal != null) {
            try {
                User user = userService.findByLogin(jwtTokenProvider.getLogin(token));
                if (animalService.checkAccessToAnimal(user, id)) {
                    animalService.deleteAnimal(id);
                    return new ResponseEntity<>("Animal successfully deleted", HttpStatus.OK);
                } else {
                    return ResponseEntity.status(403).body("You dont have access");
                }
            } catch (UserNotFoundException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        } else {
            return ResponseEntity.badRequest().body("Animal not found");
        }
    }
    
    @GetMapping("/{farm_id}/all")
    public Object animals(String token, @PathVariable Long farm_id) {
        try {
            User user = userService.findByLogin(jwtTokenProvider.getLogin(token));
            Farm farm = farmService.findFarmById(user, farm_id);
            return animalService.findAllAnimalByFarm(farm);
        } catch (FarmNotFoundException | AccessToFarmException | UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public Object getAnimalById(String token, @PathVariable("id") Long id) {
        Animal animal = animalService.findAnimalById(id);
        if (animal != null) {
            try {
                User user = userService.findByLogin(jwtTokenProvider.getLogin(token));
                if (animalService.checkAccessToAnimal(user, id)) {
                    return animalResponseReturner(animal);
                } else {
                    return ResponseEntity.status(403).body("You dont have access");
                }
            } catch (UserNotFoundException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        } else {
            return ResponseEntity.badRequest().body("Animal not found");
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
        response.put("Profit", String.valueOf(animalService.profitCounter(animal)));
        response.put("Expense", String.valueOf(animalService.expensesCounter(animal)));
        response.put("Net profit", String.valueOf(animalService.netProfitCounter(animal)));
        return ResponseEntity.ok(response);
    }
}