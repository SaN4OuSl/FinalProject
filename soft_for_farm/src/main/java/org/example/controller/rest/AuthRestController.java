package org.example.controller.rest;

import org.example.entity.User;
import org.example.exception.user.DuplicateUserLogin;
import org.example.exception.user.UserNotFoundException;
import org.example.exception.user.UserPasswordSmall;
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
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthRestController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/registration")
    public Object registration(@Valid @RequestBody User user) {
        try {
            userService.registration(user);
            return responseReturner(user);
        } catch (DuplicateUserLogin | UserPasswordSmall e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public Object login(@Valid @RequestBody User enteredUser) {
        try {
            String login = enteredUser.getLogin();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, enteredUser.getPassword()));
            User user = userService.findByLogin(login);
            return responseReturner(user);
        } catch (UserNotFoundException | AuthenticationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private Object responseReturner(User user) {
        Map<String, String> response = new HashMap<>();
        response.put("created", user.getCreated().toString());
        response.put("password", user.getPassword());
        response.put("login", user.getLogin());
        response.put("id", String.valueOf(user.getId()));
        return ResponseEntity.ok(response);
    }
}
