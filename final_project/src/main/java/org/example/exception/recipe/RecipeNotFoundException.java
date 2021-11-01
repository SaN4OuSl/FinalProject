package org.example.exception.recipe;

public class RecipeNotFoundException extends Exception{
    public RecipeNotFoundException(String message) {
        super(message);
    }
}
