package org.example.service;


import org.example.entity.auth.User;
import org.example.exception.DuplicateUserLogin;
import org.example.exception.UserNotFoundException;
import org.example.exception.UserPasswordSmall;

public interface UserService {

    User registration(User user) throws DuplicateUserLogin, UserPasswordSmall;

    User registrationAdmin(User user) throws DuplicateUserLogin, UserPasswordSmall;

    User findByLogin(String login) throws UserNotFoundException;

    User findById(Long id) throws UserNotFoundException;

    void deleteById(Long id) throws UserNotFoundException;

    void updateUser(User user) throws UserNotFoundException;

}
