package org.example.controller.rest;

import io.swagger.v3.oas.annotations.Parameter;
import org.example.entity.Farm;
import org.example.exception.farm.AccessToFarmException;
import org.example.exception.farm.FarmNotFoundException;
import org.example.exception.user.UserNotFoundException;
import org.example.service.FarmService;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/farm")
public class FarmRestController {
    
    private final FarmService farmService;
    
    public FarmRestController(FarmService farmService) {
        this.farmService = farmService;
    }
    
    @GetMapping("/farms")
    @PageableAsQueryParam
    public Object farms(@Parameter(hidden = true) Pageable pageable) {
        try {
            return farmService.findAllPageable(pageable);
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public Object getFarmById(@PathVariable("id") Long id) {
        try {
            Farm farm = farmService.findFarmById(id);
            Map<String, String> response = new HashMap<>();
            response.put("Id", String.valueOf(farm.getId()));
            response.put("Farm name", farm.getFarmName());
            response.put("Address", farm.getAddress());
            response.put("Year of statistic", farm.getYearOfStatistic());
            response.put("Profit", String.valueOf(farmService.profitCounter(farm)));
            response.put("Expenses", String.valueOf(farmService.expensesCounter(farm)));
            response.put("Net profit", String.valueOf(farmService.netProfitCounter(farm)));
            return ResponseEntity.ok(response);
        } catch (UserNotFoundException | FarmNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (AccessToFarmException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }
    
    @PostMapping(value = "/new")
    public Object createFarm(@Valid @RequestBody Farm farm, BindingResult result) {
        try {
            if (result.hasErrors()) {
                return ResponseEntity.badRequest().body("Errors in fields");
            } else {
                farmService.addFarm(farm);
                return responseReturner(farm);
            }
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PatchMapping(value = "/{id}")
    public Object updateFarm(@Valid @RequestBody Farm farm, BindingResult result, @PathVariable("id") Long id) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Errors in fields");
        } else {
            try {
                farmService.updateFarm(id, farm);
                return new ResponseEntity<>("Farm successfully updated", HttpStatus.OK);
            } catch (FarmNotFoundException | UserNotFoundException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            } catch (AccessToFarmException e) {
                return ResponseEntity.status(403).body(e.getMessage());
            }
        }
    }
    
    @DeleteMapping(value = "/{id}")
    public Object deleteFarm(@PathVariable("id") Long id) {
        try {
            farmService.deleteFarm(id);
            return new ResponseEntity<>("Farm successfully deleted", HttpStatus.OK);
        } catch (UserNotFoundException | FarmNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (AccessToFarmException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }
    
    @GetMapping(value = "/findByYear")
    @PageableAsQueryParam
    public Object findFarmsByYear(String year, @Parameter(hidden = true) Pageable pageable) {
        try {
            return farmService.findFarmsByYear(year, pageable);
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping(value = "/findFarmByName")
    @PageableAsQueryParam
    public Object findFarmsByFarmName(String farmName, @Parameter(hidden = true) Pageable pageable) {
        try {
            return farmService.findFarmsByFarmName(farmName, pageable);
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    private Object responseReturner(Farm farm) {
        Map<String, String> response = new HashMap<>();
        response.put("Id", String.valueOf(farm.getId()));
        response.put("Farm name", farm.getFarmName());
        response.put("Address", farm.getAddress());
        response.put("Year of statistic", farm.getYearOfStatistic());
        return ResponseEntity.ok(response);
    }
}