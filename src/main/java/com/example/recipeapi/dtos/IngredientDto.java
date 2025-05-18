package com.example.recipeapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientDto {
    private Long id;
    private String name;
    //private String amount; // for use inside recipe context
    private Integer quantity;
}
