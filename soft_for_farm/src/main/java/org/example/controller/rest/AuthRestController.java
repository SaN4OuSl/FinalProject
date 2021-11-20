package org.example.controller.rest;

import org.example.entity.User;
import org.example.exception.user.DuplicateUserLogin;
import org.example.exception.user.UserNotFoundException;
import org.example.exception.user.UserPasswordSmall;
import org.example.config.security.jwt.JwtTokenProvider;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthRestController {
    
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    
    @Autowired
    public AuthRestController(UserService userService, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }
    
    @PostMapping("/login")
    public Object login(@RequestBody User enteredUser) {
        String username = enteredUser.getLogin();
        User user;
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, enteredUser.getPassword()));
            user = userService.findByLogin(username);
        } catch (UserNotFoundException | AuthenticationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Unknown error");
        }
        
        return responseReturner(username, user, jwtTokenProvider, user.getId());
    }
    
    static Object responseReturner(String username, User user, JwtTokenProvider jwtTokenProvider, Long id) {
        String token = jwtTokenProvider.createToken(username, user.getRoles());
        
        Map<String, String> response = new HashMap<>();
        response.put("id", String.valueOf(id));
        response.put("login", username);
        response.put("token", token);
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/registration")
    public Object registration(@Valid @RequestBody User enteredUser) {
        User registrationUser;
        try {
            registrationUser = userService.registration(enteredUser);
        } catch (UserPasswordSmall | DuplicateUserLogin e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Unknown error");
        }
        String login = registrationUser.getLogin();
        
        return responseReturner(login, registrationUser, jwtTokenProvider, registrationUser.getId());
    }
}