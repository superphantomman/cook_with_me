package com.superphantomman.cook_with_me.sections.recipe.repositories;

import com.superphantomman.cook_with_me.sections.recipe.models.entities.RecipeInformationUnconfirmed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RecipeInformationUnconfirmedRepository extends JpaRepository<RecipeInformationUnconfirmed, Long> {
}
