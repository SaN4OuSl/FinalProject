package org.example.exception.user;

public class DuplicateUserLogin extends Exception{
    public DuplicateUserLogin(String message) {
        super(message);
    }
}
