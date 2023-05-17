package com.superphantomman.cook_with_me.sections.ingredient.repositories;

import com.superphantomman.cook_with_me.sections.ingredient.models.entities.IngredientUnconfirmed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IngredientUnconfirmedRepository extends JpaRepository<IngredientUnconfirmed, Long> {
}
