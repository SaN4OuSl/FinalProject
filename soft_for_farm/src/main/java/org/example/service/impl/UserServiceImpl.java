package org.example.service.impl;


import org.example.exception.user.AccessToUserException;
import org.example.exception.user.DuplicateUserLogin;
import org.example.exception.user.UserNotFoundException;
import org.example.exception.user.UserPasswordSmall;
import org.example.model.Role;
import org.example.model.User;
import org.example.repository.auth.RoleRepository;
import org.example.repository.auth.UserRepository;
import org.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
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
    public User registrationAdmin(User userAdmin, User user) throws DuplicateUserLogin, UserPasswordSmall, UserNotFoundException, AccessToUserException {
        try {
            if (isAdmin(userAdmin)) {
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
            } else {
                throw new AccessToUserException("You dont have enough rights");
            }
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("You dont have enough rights");
        }
    }
    
    @Override
    public Page<User> findAllPageable(User user, Pageable pageable) {
        if (user.getRoles().get(0).getName().equals("ROLE_ADMIN")) {
            LOGGER.info("Read all users");
            return userRepository.findAllUsers(user, pageable);
        } else
            return null;
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
    public void deleteById(User user, Long id) throws UserNotFoundException, AccessToUserException {
        if (isAdmin(user)) {
            userRepository.deleteById(id);
            LOGGER.info("IN deleteById: deleted with id: {}", id);
        } else {
            throw new AccessToUserException("You dont have enough rights");
        }
    }
    
    @Override
    public boolean isAdmin(User user) throws UserNotFoundException {
        if (user.getRoles().size() == 1) {
            return user.getRoles().get(0).getName().equals("ROLE_ADMIN");
        } else {
            return user.getRoles().get(0).getName().equals("ROLE_ADMIN") ||
                    user.getRoles().get(1).getName().equals("ROLE_ADMIN");
        }
    }
}
