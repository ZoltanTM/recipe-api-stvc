package com.example.recipeapi.controllers;

public class IngredientNotFoundException extends RuntimeException {
    public IngredientNotFoundException() { //String message
        super("Ingredient Not Found.");
    }
}
