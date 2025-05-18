package com.example.recipeapi.entities;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class RecipeIngredientKey implements Serializable {
    private UUID recipe;
    private Long ingredient;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeIngredientKey that = (RecipeIngredientKey) o;
        return recipe.equals(that.recipe) && ingredient.equals(that.ingredient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipe, ingredient);
    }
}
