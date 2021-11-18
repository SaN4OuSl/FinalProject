package org.example.controller.rest;

import io.swagger.v3.oas.annotations.Parameter;
import org.example.entity.User;
import org.example.exception.user.UserNotFoundException;
import org.example.security.jwt.JwtTokenProvider;
import org.example.service.UserService;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
    public Page<User> readPageable(String token, @Parameter(hidden = true) Pageable pageable) {
        User user;
        try {
            user = userService.findByLogin(jwtTokenProvider.getUsername(token));
        } catch (UserNotFoundException e) {
            return null;
        }
        return userService.findAllPageable(user, pageable);
    }
    
}