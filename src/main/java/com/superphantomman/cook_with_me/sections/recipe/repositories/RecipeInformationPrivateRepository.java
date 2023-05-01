package com.superphantomman.cook_with_me.sections.recipe.repositories;

import com.superphantomman.cook_with_me.sections.recipe.models.entities.RecipeInformationPrivate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RecipeInformationPrivateRepository extends JpaRepository<RecipeInformationPrivate, Long> {
}
