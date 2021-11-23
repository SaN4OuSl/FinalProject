package org.example.controller.rest;

import org.example.entity.User;
import org.example.exception.user.*;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserRestController {
    
    private final UserService userService;
    
    public UserRestController(UserService userService) {
        this.userService = userService;
    }
    
    @DeleteMapping(value = "/user/{id}")
    public Object deleteUser(@PathVariable("id") Long id) {
        try {
            User user = userService.getUserByAuthentication();
            userService.deleteById(user, id);
            return new ResponseEntity<>("User successfully deleted", HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NotEnoughRights e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }
    
    @PatchMapping(value = "/userUpdate/{id}")
    public Object updateUser(@RequestBody User newUser, @PathVariable("id") Long id) {
        try {
            User user = userService.getUserByAuthentication();
            userService.updateUserById(id, newUser, user);
            return new ResponseEntity<>("User successfully updated", HttpStatus.OK);
        } catch (UserNotFoundException | UserPasswordSmall | DuplicateUserLogin | UserLoginSmall e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NotEnoughRights e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }
}
