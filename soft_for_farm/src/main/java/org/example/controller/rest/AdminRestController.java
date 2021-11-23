package org.example.controller.rest;

import io.swagger.v3.oas.annotations.Parameter;
import org.example.config.security.jwt.JwtTokenProvider;
import org.example.entity.User;
import org.example.exception.user.DuplicateUserLogin;
import org.example.exception.user.UserNotFoundException;
import org.example.exception.user.UserPasswordSmall;
import org.example.service.UserService;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/admin")
public class AdminRestController {
    
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    
    public AdminRestController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    
    @GetMapping("/users")
    @PageableAsQueryParam
    public Page<User> readPageable(@Parameter(hidden = true) Pageable pageable) {
        return userService.findAllPageable(pageable);
    }
    
    @PostMapping(value = "/new")
    public Object createAdmin(@RequestBody User user) {
        User registrationAdmin;
        try {
            registrationAdmin = userService.registrationAdmin(user);
        } catch (UserPasswordSmall | DuplicateUserLogin | UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Unknown error");
        }
        String login = registrationAdmin.getLogin();
        
        String newToken = jwtTokenProvider.createToken(login, user.getRoles());
        
        Map<String, String> response = new HashMap<>();
        response.put("id", String.valueOf(registrationAdmin.getId()));
        response.put("login", login);
        response.put("token", newToken);
        
        return ResponseEntity.ok(response);
    }
    
    @PatchMapping(value = "/addAdminRole/{id}")
    public Object addAdminRole(@PathVariable("id") Long id) {
        try {
            userService.addAdminRole(userService.findUserById(id));
            return new ResponseEntity<>("New admin added successfully", HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/users/{id}")
    public Object getUserById(@PathVariable("id") Long id) {
        try {
            return userService.findUserById(id);
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}