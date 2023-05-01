package com.superphantomman.cook_with_me.sections.recipe.repositories;

import com.superphantomman.cook_with_me.sections.recipe.models.entities.RecipeInformationConfirmed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RecipeInformationConfirmedRepository extends JpaRepository<RecipeInformationConfirmed, Long>{
}
