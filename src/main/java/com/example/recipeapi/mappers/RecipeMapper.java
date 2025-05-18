package com.example.recipeapi.mappers;

import com.example.recipeapi.dtos.IngredientDto;
import com.example.recipeapi.dtos.RecipeDto;
import com.example.recipeapi.entities.Ingredient;
import com.example.recipeapi.entities.Recipe;
import com.example.recipeapi.entities.RecipeIngredient;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface RecipeMapper {
    //@Mapping(source = "ingredients", target = "ingredients", qualifiedByName = "mapIngredients")
    //@Mapping(source = "dateCreated", target = "dateCreated", qualifiedByName = "mapLocalDateTimeToTimestamp")
    RecipeDto toDto(Recipe recipe);

//    @Named("mapIngredients")
//    default List<IngredientDto> mapIngredients(List<RecipeIngredient> recipeIngredients) {
//        if (recipeIngredients == null) {
//            return new ArrayList<>();
//        }
//        return recipeIngredients.stream()
//                .map(this::mapIngredient)
//                .filter(dto -> dto != null)
//                .toList();
//    }

    @Named("mapIngredient")//removed s
    default IngredientDto mapIngredient(RecipeIngredient recipeIngredient) {
        if (recipeIngredient == null || recipeIngredient.getIngredient() == null) {
            return null;
        }
//        return new IngredientDto(
//                recipeIngredient.getIngredient().getId(),
//                recipeIngredient.getIngredient().getName(),
//                recipeIngredient.getQuantity()
//        );
        IngredientDto dto = new IngredientDto();
        Ingredient ingredient = recipeIngredient.getIngredient();
        dto.setId(ingredient.getId());
        dto.setName(ingredient.getName());
        dto.setQuantity(recipeIngredient.getQuantity());
        return dto;
    }

    @Named("calculateTotalTime")
    default Integer calculateTotalTime(Recipe recipe) {
        if (recipe == null || recipe.getPrepTimeMin() == null || recipe.getCookTimeMin() == null) {
            return null;
        }
        return recipe.getPrepTimeMin() + recipe.getCookTimeMin();
    }

    //@Mapping(source = "dateCreated", target = "dateCreated", ignore = true)
    //@Mapping(target = "ingredients", ignore = true) // Handled in service
    Recipe toEntity(RecipeDto dto);
}

//@Component
//public class RecipeMapper {
//    public RecipeDto toDto(Recipe recipe) {
//        //
//        RecipeDto dto = new RecipeDto();
//        dto.setId(recipe.getId());
//        dto.setName(recipe.getName());
//        dto.setDescription(recipe.getDescription());
//        dto.setPrepTimeMin(recipe.getPrepTimeMin());
//        dto.setCookTimeMin(recipe.getCookTimeMin());
//
//        List<IngredientDto> ingredientDtos = recipe.getIngredients().stream()
//                .map(ri -> {
//                    IngredientDto idto = new IngredientDto();
//                    idto.setId(ri.getIngredient().getId());
//                    idto.setName(ri.getIngredient().getName());
//                    idto.setAmount(ri.getAmount());
//                    return idto;
//                }).toList();
//
//        dto.setIngredients(ingredientDtos);
//        return dto;
//    }
//
//    // You can add a reverse mapping if needed
//}

