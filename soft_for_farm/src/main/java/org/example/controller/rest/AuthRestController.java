package org.example.controller.rest;

import org.example.entity.User;
import org.example.exception.user.DuplicateUserLogin;
import org.example.exception.user.UserPasswordSmall;
import org.example.service.UserService;
import org.example.service.impl.UserDetailsServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthRestController {
    
    private final UserService userService;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    
    public AuthRestController(UserService userService, UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userService = userService;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }
    
    @PostMapping("/registration")
    public Object registration(@Valid @RequestBody User user) {
        try {
            userService.registration(user);
            Map<String, String> response = new HashMap<>();
            response.put("created", user.getCreated().toString());
            response.put("password", user.getPassword());
            response.put("login", user.getLogin());
            response.put("id", String.valueOf(user.getId()));
            return ResponseEntity.ok(response);
        } catch (DuplicateUserLogin | UserPasswordSmall e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
