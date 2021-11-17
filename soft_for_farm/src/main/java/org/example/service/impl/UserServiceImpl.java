package org.example.service.impl;


import org.example.entity.Role;
import org.example.entity.User;
import org.example.exception.user.DuplicateUserLogin;
import org.example.exception.user.UserNotFoundException;
import org.example.exception.user.UserPasswordSmall;
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
    
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    
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
        regUser(user, role);
        return user;
    }
    
    @Override
    public void registrationAdmin(User userAdmin, User user) throws DuplicateUserLogin, UserPasswordSmall {
        if (user.getPassword().length() < 8) {
            LOGGER.warn("IN registrationAdmin user enter small password");
            throw new UserPasswordSmall("Password cannot be less than 8 symbols");
        }
        if (userRepository.existsUserByLogin(user.getLogin())) {
            LOGGER.warn("IN registrationAdmin user with login {} exist", user.getLogin());
            throw new DuplicateUserLogin("User with login:" + user.getLogin() + " exist");
        }
        
        Role role = roleRepository.findByName("ROLE_ADMIN");
        regUser(user, role);
    }
    
    @Override
    public Page<User> findAllPageable(User user, Pageable pageable) {
        LOGGER.info("Read all users");
        return userRepository.findAll(pageable);
    }
    
    private void regUser(User user, Role role) {
        List<Role> roleList = new ArrayList<>();
        
        roleList.add(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roleList);
        user.setCreated(Date.from(Instant.now()));
        
        User regUser = userRepository.save(user);
        
        LOGGER.info("IN registration: user by id : {} successfully registered", regUser.getId());
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
    public User findUserById(Long id) throws UserNotFoundException {
        User resultUser = userRepository.findUserById(id);
        if (resultUser == null) {
            LOGGER.warn("IN findByLogin user not found by id: {}", id);
            throw new UserNotFoundException("User with id " + id + " dont found");
        }
        LOGGER.info("IN findByLogin: found user by id: {}", id);
        return resultUser;
    }
    
    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
        LOGGER.info("IN deleteById: deleted with id: {}", id);
    }
    
    @Override
    public void updateUserById(Long id, User user) throws UserNotFoundException {
        if (userRepository.existsById(id)) {
            LOGGER.info("Start update user: " + id);
            User newUser;
            try {
                newUser = findUserById(id);
            } catch (UserNotFoundException e) {
                LOGGER.warn("IN findByLogin user not found by id: {}", id);
                throw new UserNotFoundException("User with login " + id + " dont found");
            }
            newUser.setLogin(user.getLogin());
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(newUser);
            LOGGER.info("End update user: " + id);
        } else {
            LOGGER.warn("user doesn't exists");
        }
    }
    
    @Override
    public void addAdminRole(User user) {
        LOGGER.info("Start add admin role for user: " + user.getLogin());
        List<Role> roleList = new ArrayList<>();
        Role role = roleRepository.findByName("ROLE_ADMIN");
        roleList.add(role);
        user.setRoles(roleList);
        userRepository.save(user);
        LOGGER.info("End add admin role for user: " + user.getLogin());
    }   
}
