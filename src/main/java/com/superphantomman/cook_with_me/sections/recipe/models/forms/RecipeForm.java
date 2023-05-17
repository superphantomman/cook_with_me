package com.superphantomman.cook_with_me.sections.recipe.models.forms;

import com.superphantomman.cook_with_me.exceptions.FormConversionException;
import com.superphantomman.cook_with_me.sections.ingredient.IngredientDaoService;
import com.superphantomman.cook_with_me.sections.ingredient.models.entities.Ingredient;
import com.superphantomman.cook_with_me.sections.ingredient.models.forms.IngredientRecipeForm;
import com.superphantomman.cook_with_me.sections.recipe.models.entities.Recipe;
import com.superphantomman.cook_with_me.util.Form;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;


@Data
public class RecipeForm implements Form<Recipe> {

    @NotBlank(message = "Description is required")
    private String description;

    private List<IngredientRecipeForm> ingredientForms = new ArrayList<>();

    @Getter(PRIVATE)
    private IngredientDaoService daoService;

    public Recipe toEntity(){
        if(daoService == null){
            throw new FormConversionException("Dao service should be passed for toEntity conversion");
        }



        final Recipe r = new Recipe(this.description);
        ingredientForms.forEach(
                form -> {
                    form.setRecipe(r);
                    Ingredient i = daoService.get(form.getIngredientName());
                    form.setIngredient(i);
                    r.addIngredient(form.toEntity());
                }
        );
        return r;
    }

    public boolean addIngredient(@Valid IngredientRecipeForm form){
        return ingredientForms.add(form);
    }

    public List<IngredientRecipeForm> getIngredientForms() {
        return Collections.unmodifiableList(ingredientForms);
    }
}
