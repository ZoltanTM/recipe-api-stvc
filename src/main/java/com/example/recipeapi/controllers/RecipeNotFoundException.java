package com.example.recipeapi.controllers;

public class RecipeNotFoundException extends RuntimeException {
    public RecipeNotFoundException() {
        super("Recipe not found");
    }
}
