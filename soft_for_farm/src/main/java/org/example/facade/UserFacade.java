package org.example.facade;

import org.example.dto.request.UserDtoRequest;
import org.example.dto.response.ResponseUserAdmin;
import org.example.entity.auth.User;
import org.example.exception.user.DuplicateUserLogin;
import org.example.exception.user.UserNotFoundException;
import org.example.exception.user.UserPasswordSmall;

import java.util.List;

public interface UserFacade {
    UserDtoRequest registration(UserDtoRequest dto) throws DuplicateUserLogin, UserPasswordSmall;
    
    UserDtoRequest registrationAdmin(UserDtoRequest dto) throws DuplicateUserLogin, UserPasswordSmall;
    
    List<ResponseUserAdmin> getAll() throws UserNotFoundException;
    
    UserDtoRequest findByLogin(String login) throws UserNotFoundException;
    
    UserDtoRequest findById(Long id) throws UserNotFoundException;
    
    void deleteById(Long id) throws UserNotFoundException;
    
    void updateUser(UserDtoRequest dto) throws UserNotFoundException;
}
