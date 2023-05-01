package com.superphantomman.cook_with_me.sections.recipe;


import com.superphantomman.cook_with_me.sections.recipe.repositories.RecipeInformationPrivateRepository;
import com.superphantomman.cook_with_me.sections.recipe.repositories.RecipeInformationRepository;
import com.superphantomman.cook_with_me.sections.recipe.repositories.RecipeInformationUnconfirmedRepository;
import com.superphantomman.cook_with_me.sections.recipe.services.RecipeInformationDaoService;
import com.superphantomman.cook_with_me.sections.recipe.services.RecipeDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class RecipeServiceTest {

    @Autowired
    RecipeDaoService recipeDaoService;

    @Autowired
    RecipeInformationDaoService informationService;

    @Autowired
    RecipeInformationPrivateRepository repositoryPrivate;

    @Autowired
    RecipeInformationUnconfirmedRepository repositoryUnconfirmed;

    @Autowired
    RecipeInformationRepository informationRepository;

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


}




