package com.superphantomman.cook_with_me.util;

import com.superphantomman.cook_with_me.sections.ingredient.models.entities.Ingredient;
import com.superphantomman.cook_with_me.sections.recipe.models.entities.RecipeInformation;

import java.util.Objects;

public class Entities {
    public static boolean compareEntities(Ingredient i1, Ingredient i2) {
        return Objects.equals(i1.state(), i2.state())
                && Objects.equals(i1.getName(), i2.getName())
                && Objects.equals(i1.getCalories(), i2.getCalories())
                && Objects.equals(i1.getMeasurementType(), i2.getMeasurementType());
    }

    public static boolean compareEntities(RecipeInformation ri1, RecipeInformation ri2) {

        return Objects.equals(ri1.state(), ri2.state())
                && Objects.equals(ri1.getName(), ri2.getName())
                && Objects.equals(ri1.getRecipe(), ri2.getRecipe());
    }
}
