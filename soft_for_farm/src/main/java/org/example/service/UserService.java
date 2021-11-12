package org.example.service;


import org.example.exception.user.AccessToUserException;
import org.example.entity.User;
import org.example.exception.user.DuplicateUserLogin;
import org.example.exception.user.UserNotFoundException;
import org.example.exception.user.UserPasswordSmall;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    void registration(User user) throws DuplicateUserLogin, UserPasswordSmall;

    void registrationAdmin(User userAdmin, User user) throws DuplicateUserLogin, UserPasswordSmall, UserNotFoundException, AccessToUserException;

    User findByLogin(String login) throws UserNotFoundException;

    void deleteById(User user, Long id) throws UserNotFoundException, AccessToUserException;

    Page<User> findAllPageable (User user, Pageable pageable);
    boolean isAdmin(User user) throws UserNotFoundException;
}
