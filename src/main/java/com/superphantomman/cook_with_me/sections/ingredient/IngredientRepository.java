package com.superphantomman.cook_with_me.sections.ingredient;

import com.superphantomman.cook_with_me.sections.ingredient.pojos.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    @Query("SELECT i FROM ingredient_confirmed i")
    List<Ingredient> findAll();
}
