package com.superphantomman.cook_with_me.sections.recipe;


import com.superphantomman.cook_with_me.sections.ingredient.pojos.Ingredient;
import com.superphantomman.cook_with_me.sections.ingredient.pojos.IngredientRecipe;
import com.superphantomman.cook_with_me.sections.recipe.pojos.Recipe;
import com.superphantomman.cook_with_me.sections.recipe.pojos.RecipeInformation;
import com.superphantomman.cook_with_me.sections.recipe.pojos.RecipeInformationRepository;
import com.superphantomman.cook_with_me.util.AbstractDaoService;
import com.superphantomman.cook_with_me.util.MeasurementType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class RecipeService extends AbstractDaoService<Recipe> {

    private final RecipeInformationRepository informationRepository;
    @Autowired
    public RecipeService(RecipeRepository recipeRepository,
                         RecipeInformationRepository informationRepository) {
        super(recipeRepository);
        this.informationRepository = informationRepository;
    }

    public boolean add(Recipe r, RecipeInformation ri){

        ri.setRecipe(r);
        repository.save(r);
        informationRepository.save(ri);

        return contains(r);
    }

    public boolean addToRecipe(Long recipeId, Ingredient i, Float weight, MeasurementType mt){
        return get(recipeId).addIngredient(i, weight, mt);
    }
    public boolean addToRecipe(Long recipeId, IngredientRecipe i){
        return get(recipeId).addIngredient(i);
    }

    public boolean removeFromRecipe(Long recipeId, Ingredient i){
        return get(recipeId).removeIngredient(i);
    }

   public List<IngredientRecipe> recipeIngredients(Long recipeId){
        return get(recipeId).getIngredientRecipes();
    }

    public List<? extends Recipe> getAllWithIngredient(Ingredient i){
        List<Recipe> result = new ArrayList<>();

        for (Recipe r : getAll()){
            boolean isPresent = r
                    .getIngredientRecipes().stream()
                    .anyMatch(ir -> ir.getIngredient().equals(i));
            if ( isPresent )
                result.add(r);
        }
        return result;
    }

    @Override
    public List<? extends Recipe> getAll(String search) {
        return getAll().stream()
                        .filter( r -> r.getRecipeInformation().getName().contains(search)).toList();
    }

    /*
    * Recipe must be added with recipeInformation at once not as distinct
    * */
    @Override
    public boolean add(Recipe e) {
        throw new UnsupportedOperationException();
    }

}
