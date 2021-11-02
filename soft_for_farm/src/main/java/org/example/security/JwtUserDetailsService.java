package org.example.security;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.auth.User;
import org.example.exception.user.UserNotFoundException;
import org.example.security.jwt.JwtUser;
import org.example.security.jwt.JwtUserFactory;
import org.example.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user;
        try {
            user = userService.findByLogin(login);
        } catch (UserNotFoundException e) {
            throw new UsernameNotFoundException("User with this login: " + login + " not found");
        }

        if (user == null) {
            throw new UsernameNotFoundException("User with this login: " + login + " not found");
        }

        JwtUser jwtUser = JwtUserFactory.create(user);
        log.info("IN loadUserByUsername: loaded user with id {}", user.getId());

        return jwtUser;
    }
}
