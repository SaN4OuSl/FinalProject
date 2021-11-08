package org.example.service;


import org.example.model.User;
import org.example.exception.user.DuplicateUserLogin;
import org.example.exception.user.UserNotFoundException;
import org.example.exception.user.UserPasswordSmall;

public interface UserService {

    User registration(User user) throws DuplicateUserLogin, UserPasswordSmall;

    User registrationAdmin(User user) throws DuplicateUserLogin, UserPasswordSmall;

    User findByLogin(String login) throws UserNotFoundException;

    User findById(Long id) throws UserNotFoundException;

    void deleteById(Long id) throws UserNotFoundException;

    void updateUser(User user) throws UserNotFoundException;

}
