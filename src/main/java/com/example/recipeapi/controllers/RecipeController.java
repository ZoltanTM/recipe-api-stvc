package com.example.recipeapi.controllers;

import com.example.recipeapi.dtos.IngredientDto;
import com.example.recipeapi.dtos.RecipeDto;
import com.example.recipeapi.dtos.RecipeIngredientDto;
import com.example.recipeapi.entities.Recipe;
import com.example.recipeapi.mappers.RecipeMapper;
import com.example.recipeapi.mappers.IngredientMapper;
import com.example.recipeapi.repositories.RecipeRepository;
import com.example.recipeapi.repositories.IngredientRepository;
import com.example.recipeapi.services.RecipeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;
    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;

//    @GetMapping
//    public List<RecipeDto> getAllRecipes() {
//        return recipeService.getAllRecipes();
//    }
//
//    @GetMapping("/{id}")
//    public RecipeDto getRecipeById(@PathVariable UUID id) {
//        return recipeService.getRecipeById(id);
//    }

    @PostMapping("/{recipe_id}/items")
    public ResponseEntity<RecipeDto> addToRecipe(//used to be RecipeIngredientDto
            @PathVariable UUID cartId,
            @RequestBody AddItemToRecipeRequest request) {
        var cartItemDto = recipeService.addItemToRecipe(cartId, request.getIngredientId());

        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);
    }

    @PostMapping
    public ResponseEntity<RecipeDto> createRecipe(
            @RequestBody @Valid RecipeDto recipeDto,
            UriComponentsBuilder uriBuilder
    ) {
        //RecipeDto created = recipeService.createRecipe(recipeDto);
        var recipe = new Recipe();
        recipeRepository.save(recipe);

        var createdRecipeDto = recipeMapper.toDto(recipe);
        var uri = uriBuilder.path("/recipe/{id}").buildAndExpand(createdRecipeDto.getId().toString());//toUri doesn't work, it wanted toString

        return ResponseEntity.created(uri.toUri()).body(createdRecipeDto);
        //return new ResponseEntity<>(createdRecipeDto, HttpStatus.CREATED); //createdRecipeDto instead of created
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<IngredientDto> getIngredient(@PathVariable Long id) {
//        var ingredient = IngredientRepository.findById(id).orElse(null);
//        if (ingredient == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(IngredientMapper.toDto(ingredient));
//    }


//    @PutMapping("/{id}")
//    public RecipeDto updateRecipe(@PathVariable UUID id, @RequestBody @Valid RecipeDto dto) {
//        return recipeService.updateRecipe(id, dto);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteRecipe(@PathVariable UUID id) {
//        recipeService.deleteRecipe(id);
//        return ResponseEntity.noContent().build();
//    }

//    @GetMapping("/search")
//    public List<RecipeDto> searchRecipes(
//            @RequestParam(required = false) Integer maxPrep,
//            @RequestParam(required = false) Integer maxCook
//    ) {
//        return recipeService.searchRecipes(maxPrep, maxCook);
//    }
}
