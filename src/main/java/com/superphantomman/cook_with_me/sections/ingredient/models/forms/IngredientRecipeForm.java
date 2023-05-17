package com.superphantomman.cook_with_me.sections.ingredient.models.forms;


import com.superphantomman.cook_with_me.exceptions.FormConversionException;
import com.superphantomman.cook_with_me.sections.ingredient.models.entities.Ingredient;
import com.superphantomman.cook_with_me.sections.ingredient.models.entities.IngredientRecipe;
import com.superphantomman.cook_with_me.sections.recipe.models.entities.Recipe;
import com.superphantomman.cook_with_me.util.Form;
import com.superphantomman.cook_with_me.util.MeasurementType;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import static com.superphantomman.cook_with_me.util.Groups.GRAMS;
import static com.superphantomman.cook_with_me.util.Groups.LITERS;

/*
 * Consider the way to add ingredients to Recipe
 * */
@Data
public final class IngredientRecipeForm implements Form<IngredientRecipe> {

    @Getter
    @Pattern(regexp = "[a-zA-Z ]+", message = "Invalid name for ingredient ")
    @NotBlank(message = "Name is required")

    private String ingredientName;

    @Positive(message = "Weight must be greater than 0")
    @NotNull(message = "Weight is required")
    private Float weight;


    private Ingredient ingredient;
    private Recipe recipe;


    /*
     * User will choose from given option and will not pass it by yourself
     * */
    @NotNull(message = "Measure unit is required")
    private MeasurementType mt;


    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }


    @Override
    public IngredientRecipe toEntity() {
        if (recipe == null || ingredient == null)
            throw new FormConversionException(
                    "In order to use toIngredientRecipe");

        if ((LITERS.contains(mt) && GRAMS.contains(ingredient.getMeasurementType()))
                || (GRAMS.contains(mt) && LITERS.contains(ingredient.getMeasurementType()))
        )
            throw new FormConversionException(
                    "Inconsistency with measure type between ingredient in recipe and in database"
            );

        return new IngredientRecipe(recipe, ingredient, weight, mt);
    }
}
