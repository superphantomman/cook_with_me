package com.superphantomman.cook_with_me.sections.ingredient;


import com.superphantomman.cook_with_me.sections.ingredient.models.entities.Ingredient;
import com.superphantomman.cook_with_me.sections.ingredient.models.entities.IngredientConfirmed;
import com.superphantomman.cook_with_me.sections.ingredient.repositories.IngredientRepository;
import com.superphantomman.cook_with_me.util.MeasurementType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@Transactional
@SpringBootTest
public class IngredientDaoServiceTest {


    @Autowired
    private IngredientDaoService ingredientDaoService;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Test
    public void testAdd() {
        Ingredient ingredient = new IngredientConfirmed("Salt", 0, MeasurementType.GRAM);
        boolean added = ingredientDaoService.add(ingredient);

        assertTrue(added);
        assertNotNull(ingredient.getId());
        assertEquals(ingredient, ingredientRepository.findById(ingredient.getId()).orElse(null));
        ingredientDaoService.clear();
    }


    @Test
    public void testGetAll() {
        Ingredient ingredient1 = new IngredientConfirmed("Salt", 0, MeasurementType.GRAM);
        Ingredient ingredient2 = new IngredientConfirmed("Pepper", 0, MeasurementType.GRAM);

        ingredientDaoService.add(ingredient1);
        ingredientDaoService.add(ingredient2);

        List<? extends Ingredient> ingredients = ingredientDaoService.getAll("");
        assertEquals(2, ingredients.size());
        assertTrue(ingredients.contains(ingredient1));
        assertTrue(ingredients.contains(ingredient2));
        ingredientDaoService.clear();

    }

    @Test
    public void testGetAllSorted() {

        Ingredient ingredient1 = new IngredientConfirmed("Salt", 0, MeasurementType.GRAM);
        Ingredient ingredient2 = new IngredientConfirmed("Pepper", 0, MeasurementType.GRAM);
        ingredientDaoService.add(ingredient1);
        ingredientDaoService.add(ingredient2);

        List<? extends Ingredient> ingredients = ingredientDaoService.getAllSorted(Comparator.comparing(Ingredient::getName));
        assertEquals(2, ingredients.size());
        assertEquals(ingredient2, ingredients.get(0));
        assertEquals(ingredient1, ingredients.get(1));
        ingredientDaoService.clear();

    }

    @Test
    public void testRemove() {
        Ingredient ingredient = new IngredientConfirmed("Salt", 0, MeasurementType.GRAM);
        ingredientDaoService.add(ingredient);
        boolean removed = ingredientDaoService.remove(ingredient);

        assertTrue(removed);
        assertNull(ingredientRepository.findById(ingredient.getId()).orElse(null));
        ingredientDaoService.clear();

    }

    @Test
    public void testGet() {
        Ingredient ingredient = new IngredientConfirmed("Salt", 0, MeasurementType.GRAM);
        ingredientDaoService.add(ingredient);

        Ingredient retrievedIngredient = ingredientDaoService.get(ingredient.getId());
        assertNotNull(retrievedIngredient);
        assertEquals(ingredient, retrievedIngredient);
        ingredientDaoService.clear();

    }

    @Test
    public void testRemoveById() {
        Ingredient ingredient = new IngredientConfirmed("Salt", 0, MeasurementType.GRAM);
        ingredientDaoService.add(ingredient);
        Ingredient removedIngredient = ingredientDaoService.remove(ingredient.getId());

        assertNotNull(removedIngredient);
        assertEquals(ingredient, removedIngredient);
        assertNull(ingredientRepository.findById(ingredient.getId()).orElse(null));
        ingredientDaoService.clear();

    }

    @Test
    public void testContains() {
        Ingredient ingredient = new IngredientConfirmed("Salt", 0, MeasurementType.GRAM);
        ingredientDaoService.add(ingredient);
        boolean contains = ingredientDaoService.contains(ingredient);

        assertTrue(contains);
        ingredientDaoService.clear();

    }


}
