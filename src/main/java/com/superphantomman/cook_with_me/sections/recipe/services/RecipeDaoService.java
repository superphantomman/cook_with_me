package com.superphantomman.cook_with_me.sections.recipe.services;


import com.superphantomman.cook_with_me.sections.ingredient.models.entities.Ingredient;
import com.superphantomman.cook_with_me.sections.ingredient.models.entities.IngredientRecipe;
import com.superphantomman.cook_with_me.sections.recipe.models.entities.Recipe;
import com.superphantomman.cook_with_me.sections.recipe.models.entities.RecipeInformation;
import com.superphantomman.cook_with_me.sections.recipe.repositories.RecipeRepository;
import com.superphantomman.cook_with_me.util.AbstractDaoService;
import com.superphantomman.cook_with_me.util.MeasurementType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public final class RecipeDaoService extends AbstractDaoService<Recipe> {


    private final RecipeInformationDaoService informationService;

    @Autowired
    public RecipeDaoService(RecipeRepository recipeRepository,
                            RecipeInformationDaoService informationService) {
        super(recipeRepository);
        this.informationService = informationService;
    }

    public boolean add(Recipe r, RecipeInformation ri) {
        ri.setRecipe(r);
        informationService.add(ri);
        repository.save(r);
        return contains(r);
    }




    public boolean addToRecipe(Long recipeId, Ingredient i, Float weight, MeasurementType mt) {
        return get(recipeId).addIngredient(i, weight, mt);
    }

    public boolean addToRecipe(Long recipeId, IngredientRecipe i) {
        return get(recipeId).addIngredient(i);
    }

    public boolean removeFromRecipe(Long recipeId, Ingredient i) {
        return get(recipeId).removeIngredient(i);
    }

    public List<IngredientRecipe> recipeIngredients(Long recipeId) {
        return get(recipeId).getIngredientRecipes();
    }

    public List<? extends Recipe> getAllWithIngredient(Ingredient i) {
        List<Recipe> result = new ArrayList<>();
        for (Recipe r : getAll()) {
            boolean isPresent = r
                    .getIngredientRecipes().stream()
                    .anyMatch(ir -> i.equals(ir.getIngredient()));
            if (isPresent)
                result.add(r);
        }
        return result;
    }

    @Override
    public List<? extends Recipe> getAll(String search) {
        return informationService.getAll(search).stream().map(RecipeInformation::getRecipe).toList();
    }

    /*
     * Recipe must be added with recipeInformation at once not as distinct
     * */
    @Override
    public boolean add(Recipe e) {
        throw new UnsupportedOperationException();
    }

}
