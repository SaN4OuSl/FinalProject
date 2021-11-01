package org.example.service;


import org.example.dto.response.ResponseUserAdmin;
import org.example.entity.auth.User;
import org.example.exception.user.DuplicateUserLogin;
import org.example.exception.user.UserNotFoundException;
import org.example.exception.user.UserPasswordSmall;

import java.util.List;

public interface UserService {

    User registration(User user) throws DuplicateUserLogin, UserPasswordSmall;

    User registrationAdmin(User user) throws DuplicateUserLogin, UserPasswordSmall;

    List<ResponseUserAdmin> getAll() throws UserNotFoundException;

    User findByLogin(String login) throws UserNotFoundException;

    User findById(Long id) throws UserNotFoundException;

    void deleteById(Long id) throws UserNotFoundException;

    void updateUser(User user) throws UserNotFoundException;

}
