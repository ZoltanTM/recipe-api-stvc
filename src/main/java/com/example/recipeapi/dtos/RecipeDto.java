package com.example.recipeapi.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDto {
    private UUID id;

    private String name;

    @Size(max = 2500)
    private String description;

    @Min(0)
    private Integer prepTimeMin = 0;

    @Min(0)
    private Integer cookTimeMin = 0;

    //private Timestamp dateCreated;
    private LocalDateTime dateCreated;

    private List<@Valid IngredientDto> ingredients = new ArrayList<>();

}
