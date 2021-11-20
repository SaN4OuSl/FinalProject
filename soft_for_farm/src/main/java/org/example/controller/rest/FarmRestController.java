package org.example.controller.rest;

import io.swagger.v3.oas.annotations.Parameter;
import org.example.entity.Farm;
import org.example.entity.User;
import org.example.exception.farm.AccessToFarmException;
import org.example.exception.farm.FarmNotFoundException;
import org.example.exception.user.UserNotFoundException;
import org.example.security.jwt.JwtTokenProvider;
import org.example.service.FarmService;
import org.example.service.UserService;
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
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    
    public FarmRestController(FarmService farmService, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.farmService = farmService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }
    
    @GetMapping("/farms")
    @PageableAsQueryParam
    public Object farms(String token, @Parameter(hidden = true) Pageable pageable) {
        User user;
        try {
            user = userService.findByLogin(jwtTokenProvider.getLogin(token));
            return farmService.findAllPageable(user, pageable);
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public Object getFarmById(String token, @PathVariable("id") Long id) {
        try {
            User user = userService.findByLogin(jwtTokenProvider.getLogin(token));
            Farm farm = farmService.findFarmById(user, id);
            return responseReturner(farm);
        } catch (UserNotFoundException | FarmNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (AccessToFarmException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }
    
    @PostMapping(value = "/new")
    public Object createFarm(String token, @Valid @RequestBody Farm farm, BindingResult result) {
        User user;
        try {
            user = userService.findByLogin(jwtTokenProvider.getLogin(token));
            if (result.hasErrors()) {
                return ResponseEntity.badRequest().body("Errors in fields");
            } else {
                farmService.addFarm(user, farm);
                return responseReturner(farm);
            }
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PatchMapping(value = "/{id}")
    public Object updateFarm(String token, @Valid @RequestBody Farm farm, BindingResult result, @PathVariable("id") Long id) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Errors in fields");
        } else {
            try {
                User user = userService.findByLogin(jwtTokenProvider.getLogin(token));
                farmService.updateFarm(user, id, farm);
                return new ResponseEntity<>("Farm successfully updated", HttpStatus.OK);
            } catch (FarmNotFoundException | UserNotFoundException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            } catch (AccessToFarmException e) {
                return ResponseEntity.status(403).body(e.getMessage());
            }
        }
    }
    
    @DeleteMapping(value = "/{id}")
    public Object deleteFarm(String token, @PathVariable("id") Long id) {
        try {
            User user = userService.findByLogin(jwtTokenProvider.getLogin(token));
            farmService.deleteFarm(user, id);
            return new ResponseEntity<>("Farm successfully deleted", HttpStatus.OK);
        } catch (UserNotFoundException | FarmNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (AccessToFarmException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }
    
    @GetMapping(value = "/findByYear")
    @PageableAsQueryParam
    public Object findFarmsByYear(String token, String year, @Parameter(hidden = true) Pageable pageable) {
        User user;
        try {
            user = userService.findByLogin(jwtTokenProvider.getLogin(token));
            return farmService.findFarmsByYear(year, user, pageable);
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping(value = "/findFarmByName")
    @PageableAsQueryParam
    public Object findFarmsByFarmName(String token, String farmName, @Parameter(hidden = true) Pageable pageable) {
        User user;
        try {
            user = userService.findByLogin(jwtTokenProvider.getLogin(token));
            return farmService.findFarmsByFarmName(farmName, user, pageable);
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