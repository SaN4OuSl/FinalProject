package org.example.exception.user;

public class NotEnoughRights extends Exception{
    public NotEnoughRights(String message) {
        super(message);
    }
}
