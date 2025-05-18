package com.example.recipeapi.services;

import com.example.recipeapi.controllers.ResourceNotFoundException;
import com.example.recipeapi.dtos.IngredientDto;
import com.example.recipeapi.dtos.RecipeDto;
import com.example.recipeapi.entities.Ingredient;
import com.example.recipeapi.entities.Recipe;
import com.example.recipeapi.entities.RecipeIngredient;
import com.example.recipeapi.mappers.RecipeMapper;
import com.example.recipeapi.repositories.IngredientRepository;
import com.example.recipeapi.repositories.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl extends RecipeService { //used to be implements

    private final RecipeRepository recipeRepo;
    private final IngredientRepository ingredientRepo;
    private final RecipeMapper recipeMapper;

    //@Override
    public List<RecipeDto> getAllRecipes() {
        return recipeRepo.findAll().stream()
                .map(recipeMapper::toDto)
                .toList();
    }

    //@Override
    public RecipeDto getRecipeById(UUID id) {
        Recipe recipe = recipeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found"));
        return recipeMapper.toDto(recipe);
    }

    @Override
    public RecipeDto createRecipe(RecipeDto dto) {
        Recipe recipe = new Recipe();
        recipe.setId(UUID.randomUUID());
        recipe.setName(dto.getName());
        recipe.setDescription(dto.getDescription());
        recipe.setPrepTimeMin(dto.getPrepTimeMin());
        recipe.setCookTimeMin(dto.getCookTimeMin());
        recipe.setDateCreated(LocalDateTime.now());

        List<RecipeIngredient> ingredients = new ArrayList<>();
        for (IngredientDto iDto : dto.getIngredients()) {
            String name = iDto.getName();
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Ingredient name is required");
            }

            Ingredient ing = ingredientRepo.findByName(name)
                    .orElseGet(() -> ingredientRepo.save(new Ingredient()));//not Ingridient(name), it gets into weird constructor logic.
            RecipeIngredient ri = new RecipeIngredient();
            ri.setIngredient(ing);
            ri.setRecipe(recipe);
            ri.setQuantity(iDto.getQuantity());
            ingredients.add(ri);
        }
        recipe.setIngredients(ingredients);

        return recipeMapper.toDto(recipeRepo.save(recipe));
    }

    //@Override
    public RecipeDto updateRecipe(UUID id, RecipeDto dto) {
        Recipe existing = recipeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found"));

        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setPrepTimeMin(dto.getPrepTimeMin());
        existing.setCookTimeMin(dto.getCookTimeMin());

        // Optional: handle ingredient update
        return recipeMapper.toDto(recipeRepo.save(existing));
    }

    //@Override
    public void deleteRecipe(UUID id) {
        if (!recipeRepo.existsById(id)) {
            throw new ResourceNotFoundException("Recipe not found");
        }
        recipeRepo.deleteById(id);
    }

    //@Override
    public List<RecipeDto> searchRecipes(Integer maxPrep, Integer maxCook) {
        return recipeRepo.findByPrepTimeMinLessThanEqualAndCookTimeMinLessThanEqual(
                maxPrep != null ? maxPrep : Integer.MAX_VALUE,
                maxCook != null ? maxCook : Integer.MAX_VALUE
        ).stream().map(recipeMapper::toDto).toList();
    }
}
