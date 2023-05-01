package com.superphantomman.cook_with_me.sections.recipe;

import com.superphantomman.cook_with_me.sections.recipe.models.entities.Recipe;
import com.superphantomman.cook_with_me.sections.recipe.models.entities.RecipeInformation;
import com.superphantomman.cook_with_me.sections.recipe.models.entities.RecipeInformationConfirmed;
import com.superphantomman.cook_with_me.sections.recipe.models.entities.RecipeInformationPrivate;
import com.superphantomman.cook_with_me.sections.recipe.repositories.RecipeInformationPrivateRepository;
import com.superphantomman.cook_with_me.sections.recipe.repositories.RecipeInformationRepository;
import com.superphantomman.cook_with_me.sections.recipe.repositories.RecipeInformationUnconfirmedRepository;
import com.superphantomman.cook_with_me.sections.recipe.services.RecipeInformationDaoService;
import com.superphantomman.cook_with_me.sections.recipe.services.RecipeDaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.superphantomman.cook_with_me.util.State.*;
import static org.junit.jupiter.api.Assertions.*;


@Transactional
@SpringBootTest
public class RecipeInformationDaoServiceTest {
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
    public RecipeInformationDaoServiceTest(RecipeDaoService recipeDaoService, RecipeInformationDaoService informationService, RecipeInformationPrivateRepository repositoryPrivate, RecipeInformationUnconfirmedRepository repositoryUnconfirmed, RecipeInformationRepository informationRepository) {
        this.recipeDaoService = recipeDaoService;
        this.informationService = informationService;
        this.repositoryPrivate = repositoryPrivate;
        this.repositoryUnconfirmed = repositoryUnconfirmed;
        this.informationRepository = informationRepository;
    }

    @Test
    public void testAdd() {
        Recipe recipe = new Recipe("cut a bread");
        RecipeInformation ri1 = new RecipeInformationConfirmed("confirmed", LocalDate.now());
        ri1.setRecipe(recipe);
        assertTrue(informationService.add(ri1));
        RecipeInformation ri2 = informationService.get(1L);
        assertNotEquals(ri2, null);
        assertEquals(ri2.getName(), ri1.getName());
        assertEquals(ri2.state(), CONFIRMED);

    }

    @Test
    public void testRemove() {
        Recipe recipe = new Recipe("cut a bread");
        RecipeInformation ri = new RecipeInformationConfirmed("confirmed", LocalDate.now());
        ri.setRecipe(recipe);
        informationService.add(ri);
        ri = informationService.get(1L);
        assertNotEquals(ri, null);
        assertTrue(informationService.remove(ri));
        ri = informationService.get(1L);
        assertEquals(ri, null);
    }

    @Test
    public void testContains() {
        Recipe recipe = new Recipe("cut a bread");
        RecipeInformation ri = new RecipeInformationConfirmed("confirmed", LocalDate.now());
        ri.setRecipe(recipe);
        informationService.add(ri);
        ri = informationService.get(1L);
        assertTrue(informationService.contains(ri));

    }

    @Test
    public void testChangeState() {
        Recipe r = new Recipe("Cutting bread");
        RecipeInformation ri = new RecipeInformationPrivate("sandwich");
        recipeDaoService.add(r, ri);
        ri = informationService.get(1L);
        r = recipeDaoService.get(1L);
        assertEquals(ri.getRecipe(), r);
        assertEquals(ri.state(), PRIVATE);
        assertTrue(informationService.changeState(ri, UNCONFIRMED));
        ri = informationService.get(2L);
        assertEquals(ri.state(), UNCONFIRMED);
    }

    @Test
    public void testAddRecipeToInfo() {
        Recipe r = new Recipe("Cutting bread");
        RecipeInformation ri = new RecipeInformationConfirmed("Confirmed sandwich");
        assertTrue(informationService.add(ri));
        assertTrue(informationService.addRecipe(1L, r));
        ri = informationService.get(1L);
        informationService.getAll().forEach(System.out::println);
        assertEquals(ri.getRecipe() , recipeDaoService.get(1L) );

    }

}

