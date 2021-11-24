package org.example.service;


import org.example.entity.User;
import org.example.exception.user.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    
    User registration(User user) throws DuplicateUserLogin, UserPasswordSmall, UserLoginSmall;
    
    User registrationAdmin(User user) throws DuplicateUserLogin, UserPasswordSmall, UserNotFoundException, NotEnoughRights, UserLoginSmall;
    
    User findByLogin(String login) throws UserNotFoundException;
    
    User findUserById(Long id) throws UserNotFoundException;
    
    User getUserByAuthentication() throws UserNotFoundException;
    
    void deleteById(User userWhoDeletes, Long id) throws UserNotFoundException, NotEnoughRights;
    
    void updateUserById(Long id, User user, User userWhoUpdates) throws UserNotFoundException, NotEnoughRights, UserPasswordSmall, DuplicateUserLogin, UserLoginSmall;
    
    Page<User> findAllPageable(Pageable pageable);
    
    void addAdminRole(User user);
}
