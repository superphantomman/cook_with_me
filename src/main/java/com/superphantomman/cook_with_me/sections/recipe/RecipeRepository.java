package com.superphantomman.cook_with_me.sections.recipe;


import com.superphantomman.cook_with_me.sections.recipe.pojos.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
