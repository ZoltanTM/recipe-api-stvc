package com.example.recipeapi.repositories;

import com.example.recipeapi.entities.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {//Long is formerly RecipeIngredientKey
    List<RecipeIngredient> findByRecipeId(UUID recipeId);
}

