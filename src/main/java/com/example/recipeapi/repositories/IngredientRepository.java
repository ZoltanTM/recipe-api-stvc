package com.example.recipeapi.repositories;

import com.example.recipeapi.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    Optional<Ingredient> findByName(String name);

    static Optional<Ingredient> findById(String id) {
        return null;
    }
}
