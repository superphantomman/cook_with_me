package com.superphantomman.cook_with_me.sections.ingredient.models.entities;


import com.superphantomman.cook_with_me.sections.recipe.models.entities.Recipe;
import com.superphantomman.cook_with_me.util.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


@ToString
@NoArgsConstructor
@Table(name = "ingredient_recipe")
@Entity
public class IngredientRecipe {

    @EmbeddedId
    private IngredientRecipeId id;

    @Getter
    @Setter
    @Column(name = "weight")
    /* for sake of calculation should be converted to Integer
     to avoid floating point calculation problem
     */

    private Float weight;

    @Getter
    @Setter
    @Column(name = "measurment_type")
    private MeasurementType measurementType;


    public IngredientRecipe(Recipe recipe, Ingredient ingredient) {
        this.id = new IngredientRecipeId(recipe, ingredient);
    }

    public IngredientRecipe(Recipe recipe, Ingredient ingredient, Float weight, MeasurementType measurementType) {
        this(recipe, ingredient);
        this.weight = weight;
        this.measurementType = measurementType;
    }


    @Transient
    public Recipe getRecipe() {
        return id.getRecipe();
    }

    @Transient
    public Ingredient getIngredient() {
        return id.getIngredient();
    }

    @Transient
    public void setRecipe(Recipe r) {
        id.setRecipe(r);
    }
}
