package org.example.service;


import org.example.exception.user.AccessToUserException;
import org.example.model.User;
import org.example.exception.user.DuplicateUserLogin;
import org.example.exception.user.UserNotFoundException;
import org.example.exception.user.UserPasswordSmall;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;

public interface UserService {

    User registration(User user) throws DuplicateUserLogin, UserPasswordSmall;

    User registrationAdmin(User user) throws DuplicateUserLogin, UserPasswordSmall;

    User findByLogin(String login) throws UserNotFoundException;

    void deleteById(Principal principal, Long id) throws UserNotFoundException, AccessToUserException;

    Page<User> findAllPageable (User user, Pageable pageable);
    boolean isAdmin(Principal principal) throws UserNotFoundException;
}
