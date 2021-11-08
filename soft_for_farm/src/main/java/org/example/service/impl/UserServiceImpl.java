package org.example.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.example.model.Role;
import org.example.model.User;
import org.example.exception.user.DuplicateUserLogin;
import org.example.exception.user.UserNotFoundException;
import org.example.exception.user.UserPasswordSmall;
import org.example.repository.auth.RoleRepository;
import org.example.repository.auth.UserRepository;
import org.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public User registration(User user) throws DuplicateUserLogin, UserPasswordSmall {
        if (user.getPassword().length() < 8) {
            LOGGER.warn("IN registration user enter small password");
            throw new UserPasswordSmall("Password cannot be less than 8 symbols");
        }
        if (userRepository.existsUserByLogin(user.getLogin())) {
            LOGGER.warn("IN user with login {} exist", user.getLogin());
            throw new DuplicateUserLogin("User with login:" + user.getLogin() + " exist");
        }
        
        Role role = roleRepository.findByName("ROLE_USER");
        return regUser(user, role);
    }
    
    @Override
    public User registrationAdmin(User user) throws DuplicateUserLogin, UserPasswordSmall {
        if (user.getPassword().length() < 8) {
            LOGGER.warn("IN registrationAdmin user enter small password");
            throw new UserPasswordSmall("Password cannot be less than 8 symbols");
        }
        if (userRepository.existsUserByLogin(user.getLogin())) {
            LOGGER.warn("IN registrationAdmin user with login {} exist", user.getLogin());
            throw new DuplicateUserLogin("User with login:" + user.getLogin() + " exist");
        }
        
        Role role = roleRepository.findByName("ROLE_ADMIN");
        return regUser(user, role);
    }
    
    public void updateUser(User user) throws UserNotFoundException {
        userRepository.findById(user.getId()).orElseThrow(() -> {
            LOGGER.warn("IN updateUser user with id {} not found", user.getLogin());
            return new UserNotFoundException("User with login:" + user.getLogin() + " exist");
        });
        LOGGER.info("IN updateUser user successfully updated with id: {}", user.getId());
        userRepository.save(user);
    }
    
    private User regUser(User user, Role role) {
        List<Role> roleList = new ArrayList<>();
        
        roleList.add(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roleList);
        user.setCreated(Date.from(Instant.now()));
        
        User regUser = userRepository.save(user);
        
        LOGGER.info("IN registration: user by id : {} successfully registered", regUser.getId());
        return regUser;
    }
    
    @Override
    public User findByLogin(String login) throws UserNotFoundException {
        User resultUser = userRepository.findByLogin(login);
        if (resultUser == null) {
            LOGGER.warn("IN findByLogin user not found by login: {}", login);
            throw new UserNotFoundException("User with login " + login + " dont found");
        }
        LOGGER.info("IN findByLogin: found user by login: {} with id : {}", login, resultUser.getId());
        return resultUser;
    }
    
    @Override
    public User findById(Long id) throws UserNotFoundException {
        User result = userRepository.findById(id).orElseThrow(() -> {
            LOGGER.warn("IN findById no user found by id: {}", id);
            return new UserNotFoundException("user with id:" + id + " not found");
        });
        LOGGER.info("IN findById - user: {} found by id: {}", result, id);
        return result;
    }
    
    @Override
    public void deleteById(Long id) throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found by id: " + id));
        userRepository.save(user);
        LOGGER.info("IN deleteById: deleted with id: {}, user: {}", id, user);
    }
}
