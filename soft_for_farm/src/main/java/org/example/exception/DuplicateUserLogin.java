package org.example.exception;

public class DuplicateUserLogin extends Exception{
    public DuplicateUserLogin(String message) {
        super(message);
    }
}
