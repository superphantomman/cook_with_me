package com.superphantomman.cook_with_me.sections.recipe.models.forms;

import com.superphantomman.cook_with_me.sections.ingredient.models.entities.IngredientRecipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeForm {
    private String description;

    List<IngredientRecipe> ingredientRecipes = new ArrayList<>(7);

}
