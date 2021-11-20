package org.example.controller.rest;

import io.swagger.v3.oas.annotations.Parameter;
import org.example.entity.User;
import org.example.exception.user.*;
import org.example.config.security.jwt.JwtTokenProvider;
import org.example.service.UserService;
import org.example.service.impl.UserDetailsServiceImpl;
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
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    
    public AdminRestController(UserService userService, UserDetailsServiceImpl userDetailsService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    
    @GetMapping("/users")
    @PageableAsQueryParam
    public Page<User> readPageable(String token, @Parameter(hidden = true) Pageable pageable) {
        try {
            User user = userService.findByLogin(jwtTokenProvider.getLogin(token));
            return userService.findAllPageable(user, pageable);
        } catch (UserNotFoundException | NotEnoughRights e) {
            return null;
        }
    }
    
    @PostMapping(value = "/new")
    public Object createAdmin(String adminToken, @RequestBody User user) {
        User registrationAdmin;
        String adminLogin = jwtTokenProvider.getLogin(adminToken);
        try {
            registrationAdmin = userService.registrationAdmin(userService.findByLogin(adminLogin), user);
        } catch (UserPasswordSmall | DuplicateUserLogin | UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NotEnoughRights e) {
            return ResponseEntity.status(403).body(e.getMessage());
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
    
    @DeleteMapping(value = "/user/{id}")
    public Object deleteUser(String token, @PathVariable("id") Long id) {
        try {
            User user = userService.findByLogin(jwtTokenProvider.getLogin(token));
            userService.deleteById(user, id);
            return new ResponseEntity<>("User successfully deleted", HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NotEnoughRights e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }
    
    @PatchMapping(value = "/userUpdate/{id}")
    public Object updateUser(String token, @RequestBody User newUser, @PathVariable("id") Long id) {
        try {
            User user = userService.findByLogin(jwtTokenProvider.getLogin(token));
            userService.updateUserById(id, newUser, user);
            return new ResponseEntity<>("User successfully updated", HttpStatus.OK);
        } catch (UserNotFoundException | UserPasswordSmall | DuplicateUserLogin | UserLoginSmall e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NotEnoughRights e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }
    
    @PatchMapping(value = "/addAdminRole/{id}")
    public Object addAdminRole(String token, @PathVariable("id") Long id) {
        try {
            User userAdmin = userService.findByLogin(jwtTokenProvider.getLogin(token));
            userService.addAdminRole(userAdmin, userService.findUserById(id));
            return new ResponseEntity<>("New admin added successfully", HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NotEnoughRights e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }
    
    @GetMapping("/users/{id}")
    public Object getUserById(String token, @PathVariable("id") Long id) {
        try {
            User user = userService.findByLogin(jwtTokenProvider.getLogin(token));
            if (userDetailsService.isAdmin(user) || user.getId().equals(id))
                return userService.findUserById(id);
            else return ResponseEntity.badRequest().body("You dont have enough rights");
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}