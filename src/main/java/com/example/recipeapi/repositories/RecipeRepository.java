package com.example.recipeapi.repositories;

import com.example.recipeapi.entities.Recipe;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, UUID> {
    List<Recipe> findByPrepTimeMinLessThanEqualAndCookTimeMinLessThanEqual(int prep, int cook);

    @EntityGraph(attributePaths = "items.ingredient") //Does this have a problem in Service?
    @Query("SELECT c FROM Recipe c WHERE c.id = :recipeId")
    Recipe getRecipeWithItems(@Param("recipeId") UUID recipeId); //Used to be static?
}
