package org.example.controller;

import org.example.entity.auth.User;
import org.example.exception.user.DuplicateUserLogin;
import org.example.exception.user.UserNotFoundException;
import org.example.exception.user.UserPasswordSmall;
import org.example.model.UserLoginModel;
import org.example.security.jwt.JwtTokenProvider;
import org.example.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtTokenProvider jwtTokenProvider,
                          UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserLoginModel userLoginModel) {
        String username = userLoginModel.getUsername();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, userLoginModel.getPassword()));
        User user;
        try {
            user = userService.findByLogin(username);
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Unknown error");
        }

        return getResponseEntity(username, user, jwtTokenProvider, user.getId());
    }

    static ResponseEntity getResponseEntity(String username, User user, JwtTokenProvider jwtTokenProvider, Long id) {
        String token = jwtTokenProvider.createToken(username, user.getRoles());

        Map<String, String> response = new HashMap<>();
        response.put("id", String.valueOf(id));
        response.put("username", username);
        response.put("token", token);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/registration")
    public ResponseEntity registration(@Valid @RequestBody UserLoginModel userLoginModel) {
        String username = userLoginModel.getUsername();
        User user = new User();
        user.setLogin(username);
        user.setPassword(userLoginModel.getPassword());
        User registrationUser;
        try {
            registrationUser = userService.registration(user);
        } catch (UserPasswordSmall | DuplicateUserLogin e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Unknown error");
        }

        String token = jwtTokenProvider.createToken(username, registrationUser.getRoles());

        Map<String, String> response = new HashMap<>();
        response.put("id", String.valueOf(user.getId()));
        response.put("username", username);
        response.put("token", token);

        return ResponseEntity.ok(response);
    }

}
