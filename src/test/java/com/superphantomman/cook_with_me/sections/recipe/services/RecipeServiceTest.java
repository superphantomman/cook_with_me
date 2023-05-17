package com.superphantomman.cook_with_me.sections.recipe.services;


import com.superphantomman.cook_with_me.sections.recipe.models.entities.Recipe;
import com.superphantomman.cook_with_me.sections.recipe.models.entities.RecipeInformation;
import com.superphantomman.cook_with_me.sections.recipe.models.entities.RecipeInformationUnconfirmed;
import com.superphantomman.cook_with_me.sections.recipe.repositories.RecipeInformationPrivateRepository;
import com.superphantomman.cook_with_me.sections.recipe.repositories.RecipeInformationRepository;
import com.superphantomman.cook_with_me.sections.recipe.repositories.RecipeInformationUnconfirmedRepository;
import com.superphantomman.cook_with_me.sections.recipe.services.RecipeInformationDaoService;
import com.superphantomman.cook_with_me.sections.recipe.services.RecipeDaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@SpringBootTest
public class RecipeServiceTest {

    @Autowired
    private RecipeDaoService recipeDaoService;

    @Autowired
    private RecipeInformationDaoService informationService;

    @Autowired
    private RecipeInformationPrivateRepository repositoryPrivate;

    @Autowired
    private RecipeInformationUnconfirmedRepository repositoryUnconfirmed;

    @Autowired
    private RecipeInformationRepository informationRepository;

    @Autowired
    public RecipeServiceTest(
            RecipeDaoService recipeDaoService, RecipeInformationPrivateRepository repository, RecipeInformationRepository informationRepository,
            RecipeInformationUnconfirmedRepository repositoryUnconfirmed
    ) {
        this.recipeDaoService = recipeDaoService;
        this.repositoryPrivate = repository;
        this.informationRepository = informationRepository;
        this.repositoryUnconfirmed = repositoryUnconfirmed;
    }

    @Test
    public void addRecipeTest() {
        RecipeInformation ri = new RecipeInformationUnconfirmed("sandwich");
        Recipe r = new Recipe("cutting bread");


        assertTrue(recipeDaoService.add(r, ri));
        assertEquals(ri.getId(), 1L);
        assertEquals(r.getId(), 1L);



    }


}




