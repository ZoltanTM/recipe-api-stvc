package com.example.recipeapi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recipe", schema = "recipe_schema")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private String name;
    private String description;

    //@OneToMany(mappedBy = "recipe")
    //private Set<RecipeIngredient> recipeItems = new LinkedHashSet<RecipeIngredient>();

    @Column(name = "prep_time_min")
    private Integer prepTimeMin;

    @Column(name = "cook_time_min")
    private Integer cookTimeMin;

    @Column(name = "date_created", insertable = false, updatable = false)
    private LocalDateTime dateCreated;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeIngredient> ingredients = new ArrayList<>();

    public RecipeIngredient getItem(Long productId) {
        return ingredients.stream()
                .filter(item -> item.getIngredient().getId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    public RecipeIngredient addItem(Ingredient ingredient) {//was supposed to be RecipeItem at first?
        var recipeItem = getItem(ingredient.getId());
        if (recipeItem != null) {
            recipeItem.setQuantity(recipeItem.getQuantity() + 1);
        } else {
            recipeItem = new RecipeIngredient();//was supposed to be RecipeItem at first?
            recipeItem.setIngredient(ingredient);
            recipeItem.setQuantity(1);
            recipeItem.setRecipe(this);
            ingredients.add(recipeItem);//used to be recipeItems
        }
        return recipeItem;
    }
    @PrePersist
    public void generateId() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }
}
