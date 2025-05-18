package com.example.recipeapi.mappers;

import com.example.recipeapi.dtos.IngredientDto;
import com.example.recipeapi.entities.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IngredientMapper {
    IngredientDto toDto(Ingredient ingredient);

    Ingredient toEntity(IngredientDto ingredientDto);

    //@Mapping(target = "id", ignore = true)
    void update(IngredientDto ingredientDto, @MappingTarget Ingredient ingredient);
}
