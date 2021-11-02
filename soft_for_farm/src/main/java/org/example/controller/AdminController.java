package org.example.controller;

import org.example.dto.response.ResponseUserAdmin;
import org.example.entity.auth.User;
import org.example.exception.user.DuplicateUserLogin;
import org.example.exception.user.UserNotFoundException;
import org.example.exception.user.UserPasswordSmall;
import org.example.model.UserLoginModel;
import org.example.model.UserModel;
import org.example.security.jwt.JwtTokenProvider;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static org.example.controller.AuthController.getResponseEntity;

@RestController
@RequestMapping("/admin")
public class AdminController {
    
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    
    public AdminController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    
    @GetMapping("/users/{id}")
    public ResponseEntity getUserById(@PathVariable String id) {
        User resultUser;
        try {
            resultUser = userService.findById(Long.valueOf(id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Unknown error");
        }
        UserModel userModel = UserModel.fromUser(resultUser);
        return new ResponseEntity<>(userModel, HttpStatus.OK);
    }
    
    @DeleteMapping("/users/{id}")
    public ResponseEntity inactiveUser(@PathVariable String id) {
        try {
            userService.deleteById(Long.valueOf(id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Unknown error");
        }
        
        return new ResponseEntity<>("User successfully deleted", HttpStatus.OK);
    }
    
    @GetMapping("/users/all")
    public ResponseEntity getAllUsers() {
        List<ResponseUserAdmin> responseUserAdmins;
        try {
            responseUserAdmins = userService.getAll();
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(responseUserAdmins);
    }
    
    @PostMapping("/registration")
    public ResponseEntity registrationAdmin(@Valid @RequestBody UserLoginModel userLoginModel) {
        String username = userLoginModel.getUsername();
        User user = new User();
        user.setLogin(username);
        user.setPassword(userLoginModel.getPassword());
        User registrationUser;
        try {
            registrationUser = userService.registrationAdmin(user);
        } catch (UserPasswordSmall | DuplicateUserLogin e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Unknown error");
        }
        
        return getResponseEntity(username, registrationUser, jwtTokenProvider, user.getId());
    }
    
}