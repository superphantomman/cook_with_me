package com.superphantomman.cook_with_me.sections.ingredient;

import com.superphantomman.cook_with_me.sections.ingredient.models.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IngredientRepository  extends JpaRepository<Ingredient, Long>  {
}
