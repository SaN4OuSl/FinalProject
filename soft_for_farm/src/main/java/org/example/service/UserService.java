package org.example.service;


import org.example.entity.User;
import org.example.exception.user.DuplicateUserLogin;
import org.example.exception.user.UserNotFoundException;
import org.example.exception.user.UserPasswordSmall;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    User registration(User user) throws DuplicateUserLogin, UserPasswordSmall;

    void registrationAdmin(User userAdmin, User user) throws DuplicateUserLogin, UserPasswordSmall, UserNotFoundException;

    User findByLogin(String login) throws UserNotFoundException;

    User findUserById(Long id) throws UserNotFoundException;

    void deleteById(Long id) throws UserNotFoundException;

    void updateUserById(Long id, User user) throws UserNotFoundException;

    Page<User> findAllPageable(User user, Pageable pageable);
    
    void addAdminRole(User user);
}
