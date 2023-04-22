package com.superphantomman.cook_with_me.sections.recipe.pojos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RecipeInformationRepository extends JpaRepository<RecipeInformation, Long> {
}
