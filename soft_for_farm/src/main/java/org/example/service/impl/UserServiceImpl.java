package org.example.service.impl;


import org.example.entity.Role;
import org.example.entity.User;
import org.example.exception.user.*;
import org.example.repository.auth.RoleRepository;
import org.example.repository.auth.UserRepository;
import org.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserDetailsServiceImpl userDetailsService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    
    public UserServiceImpl(UserDetailsServiceImpl userDetailsService, UserRepository userRepository,
                           RoleRepository roleRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public User registration(User user) throws DuplicateUserLogin, UserPasswordSmall, UserLoginSmall {
        if (checkUserPaswordAndLogin(user)) {
            Role role = roleRepository.findByName("ROLE_USER");
            regUser(user, role);
            return user;
        } else {
            LOGGER.warn("IN registrationAdmin user with login {} exist", user.getLogin());
            throw new DuplicateUserLogin("User with login:" + user.getLogin() + " exist");
        }
    }
    
    @Override
    public User registrationAdmin(User user) throws DuplicateUserLogin, UserPasswordSmall, NotEnoughRights, UserLoginSmall {
        if (checkUserPaswordAndLogin(user)) {
            Role role = roleRepository.findByName("ROLE_ADMIN");
            regUser(user, role);
            return user;
        }
        throw new NotEnoughRights("You dont have rights for this action");
    }
    
    @Override
    public Page<User> findAllPageable(Pageable pageable) {
        LOGGER.info("Read all users");
        return userRepository.findAllUsers(pageable);
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
    public User getUserByAuthentication() throws UserNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return findByLogin(auth.getName());
    }
    
    @Override
    public void deleteById(User userWhoDeletes, Long id) throws UserNotFoundException, NotEnoughRights {
        if (userRepository.existsById(id)) {
            if (userDetailsService.isAdmin(userWhoDeletes) || id.equals(userWhoDeletes.getId())) {
                userRepository.deleteById(id);
                LOGGER.info("IN deleteById: deleted with id: {}", id);
            } else {
                throw new NotEnoughRights("You don't have enough rights");
            }
        } else {
            throw new UserNotFoundException("User not found");
        }
    }
    
    @Override
    public void updateUserById(Long id, User user, User userWhoUpdates) throws UserNotFoundException, NotEnoughRights, UserPasswordSmall, DuplicateUserLogin, UserLoginSmall {
        if (userDetailsService.isAdmin(userWhoUpdates) || id.equals(userWhoUpdates.getId())) {
            if (userRepository.existsById(id)) {
                if (checkUserPaswordAndLogin(user)) {
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
                }
            } else {
                LOGGER.warn("User doesn't exists");
                throw new UserNotFoundException("User with this id does not exists");
            }
        } else {
            LOGGER.warn("User don't have enough rights");
            throw new NotEnoughRights("You don't have enough rights");
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
    
    private boolean checkUserPaswordAndLogin(User user) throws DuplicateUserLogin, UserPasswordSmall, UserLoginSmall {
        if (user.getPassword().length() < 8) {
            LOGGER.warn("IN registrationAdmin user enter small password");
            throw new UserPasswordSmall("Password cannot be less than 8 symbols");
        }
        if (userRepository.existsUserByLogin(user.getLogin())) {
            LOGGER.warn("IN registrationAdmin user with login {} exist", user.getLogin());
            throw new DuplicateUserLogin("User with login:" + user.getLogin() + " exist");
        }
        if (user.getLogin().length() < 2) {
            LOGGER.warn("IN registrationAdmin user with login {} is to small", user.getLogin());
            throw new UserLoginSmall("Login cannot be less than 2 symbols");
        }
        return true;
    }
}
