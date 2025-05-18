package com.example.recipeapi.services;

import com.example.recipeapi.controllers.IngredientNotFoundException;
import com.example.recipeapi.controllers.RecipeNotFoundException;
import com.example.recipeapi.dtos.IngredientDto;
import com.example.recipeapi.dtos.RecipeDto;
import com.example.recipeapi.dtos.RecipeIngredientDto;
import com.example.recipeapi.entities.Ingredient;
import com.example.recipeapi.entities.Recipe;
import com.example.recipeapi.entities.RecipeIngredient;
import com.example.recipeapi.mappers.RecipeMapper;
import com.example.recipeapi.repositories.RecipeIngredientRepository;
import com.example.recipeapi.repositories.RecipeRepository;
import com.example.recipeapi.repositories.IngredientRepository;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class RecipeService {
//    List<RecipeDto> getAllRecipes();
//    RecipeDto getRecipeById(UUID id);
//    RecipeDto createRecipe(RecipeDto dto);
//    RecipeDto updateRecipe(UUID id, RecipeDto dto);
//    void deleteRecipe(UUID id);
//    List<RecipeDto> searchRecipes(Integer maxPrep, Integer maxCook);
    private IngredientRepository ingredientRepository;
    private RecipeRepository recipeRepository;
    private RecipeMapper recipeMapper;

    @Transactional
    public RecipeDto createRecipe(RecipeDto recipeDto) {
//        var recipe = new Recipe();
//        recipeRepository.save(recipe);
//
//        return recipeMapper.toDto(recipe);
        Recipe recipe = recipeMapper.toEntity(recipeDto);
        recipe.setDateCreated(LocalDateTime.now());

        // Handle ingredients
        List<RecipeIngredient> recipeIngredients = new ArrayList<>();
        for (IngredientDto iDto : recipeDto.getIngredients()) {
            Ingredient ingredient = ingredientRepository.findByName(iDto.getName())
                    .orElseGet(() -> {
                        Ingredient newIngredient = new Ingredient();
                        newIngredient.setName(iDto.getName());
                        return ingredientRepository.save(newIngredient);
    });
            RecipeIngredient recipeIngredient = new RecipeIngredient();
            recipeIngredient.setIngredient(ingredient);
            recipeIngredient.setRecipe(recipe);
            recipeIngredient.setQuantity(iDto.getQuantity());
            recipeIngredient.setUnit(null); // Set unit if needed
            recipeIngredients.add(recipeIngredient);
        }
        recipe.setIngredients(recipeIngredients);

        recipe = recipeRepository.save(recipe);
        return recipeMapper.toDto(recipe);
    }

    @Transactional
    public RecipeDto addItemToRecipe(UUID recipeId, Long ingredientId) {//used to be RecipeIngredientDto
        Recipe recipe = recipeRepository.getRecipeWithItems(recipeId);//.orElse(null)
        if (recipe == null) {
            throw new RecipeNotFoundException();
        }

        var ingredient = ingredientRepository.findById(ingredientId).orElse(null);
        if (ingredient == null) {
            throw new IngredientNotFoundException();
        }

        var recipeItem = recipe.addItem(ingredient);
        recipeRepository.save(recipe);
        return recipeMapper.toDto(recipeItem.getRecipe());
    }

    //Completed
    public List<RecipeDto> getAllRecipes() {
        return recipeRepository.findAll().stream()
                .map(recipeMapper::toDto)
                .collect(Collectors.toList());
    }

    public RecipeDto getRecipeById(UUID id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException());
        return recipeMapper.toDto(recipe);
    }

    @Transactional
    public RecipeDto updateRecipe(UUID id, RecipeDto dto) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException());
        recipe.setName(dto.getName());
        recipe.setDescription(dto.getDescription());
        recipe.setPrepTimeMin(dto.getPrepTimeMin());
        recipe.setCookTimeMin(dto.getCookTimeMin());

        recipe.getIngredients().clear();
        List<RecipeIngredient> recipeIngredients = new ArrayList<>();
        for (IngredientDto iDto : dto.getIngredients()) {
            Ingredient ingredient = ingredientRepository.findByName(iDto.getName())
                    .orElseGet(() -> {
                        Ingredient newIngredient = new Ingredient();
                        newIngredient.setName(iDto.getName());
                        return ingredientRepository.save(newIngredient);
                    });

            RecipeIngredient ri = new RecipeIngredient();
            ri.setIngredient(ingredient);
            ri.setRecipe(recipe);
            ri.setQuantity(iDto.getQuantity());
            ri.setUnit(null); // Set unit if needed
            recipeIngredients.add(ri);
        }
        recipe.setIngredients(recipeIngredients);

        recipe = recipeRepository.save(recipe);
        return recipeMapper.toDto(recipe);
    }

    @Transactional
    public void deleteRecipe(UUID id) {
        if (!recipeRepository.existsById(id)) {
            throw new RecipeNotFoundException();
        }
        recipeRepository.deleteById(id);
    }

    public List<RecipeDto> searchRecipes(Integer maxPrep, Integer maxCook) {
        return recipeRepository.findByPrepTimeMinLessThanEqualAndCookTimeMinLessThanEqual(maxPrep, maxCook)
                .stream()
                .map(recipeMapper::toDto)
                .collect(Collectors.toList());
    }
}
