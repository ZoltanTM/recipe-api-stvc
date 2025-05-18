package com.example.recipeapi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recipe_ingredient", schema = "recipe_schema")
//@IdClass(RecipeIngredientKey.class)
public class RecipeIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Id
    @ManyToOne//(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "recipe_id", nullable = false, columnDefinition = "BINARY(16)")
    private Recipe recipe;

    //@Id
    @ManyToOne
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "unit")
    private String unit;

    public Integer getAmount() {
        return quantity;
    }

//    public void setAmount(String amount) {
//    }
}
