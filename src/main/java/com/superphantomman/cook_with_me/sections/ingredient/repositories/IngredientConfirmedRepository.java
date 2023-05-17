package com.superphantomman.cook_with_me.sections.ingredient.repositories;

import com.superphantomman.cook_with_me.sections.ingredient.models.entities.IngredientConfirmed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientConfirmedRepository extends JpaRepository<IngredientConfirmed, Long> {
}
