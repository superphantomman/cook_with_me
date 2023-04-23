package com.superphantomman.cook_with_me.sections.ingredient;


import com.superphantomman.cook_with_me.sections.ingredient.pojos.Ingredient;
import com.superphantomman.cook_with_me.sections.ingredient.pojos.IngredientConfirmed;
import com.superphantomman.cook_with_me.util.DaoService;
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
public class IngredientServiceTest {


    @Autowired
    DaoService<Ingredient> ingredientDaoService;

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Test
    public void testAdd() {
        Ingredient ingredient = new IngredientConfirmed("Salt", 0, MeasurementType.GRAM);
        boolean added = ingredientService.add(ingredient);

        assertTrue(added);
        assertNotNull(ingredient.getId());
        assertEquals(ingredient, ingredientRepository.findById(ingredient.getId()).orElse(null));
        ingredientService.clear();
    }


    @Test
    public void testGetAll() {
        Ingredient ingredient1 = new IngredientConfirmed("Salt", 0, MeasurementType.GRAM);
        Ingredient ingredient2 = new IngredientConfirmed("Pepper", 0, MeasurementType.GRAM);

        ingredientService.add(ingredient1);
        ingredientService.add(ingredient2);

        List<? extends Ingredient> ingredients = ingredientService.getAll("");
        assertEquals(2, ingredients.size());
        assertTrue(ingredients.contains(ingredient1));
        assertTrue(ingredients.contains(ingredient2));
        ingredientService.clear();

    }

    @Test
    public void testGetAllSorted() {

        Ingredient ingredient1 = new IngredientConfirmed("Salt", 0, MeasurementType.GRAM);
        Ingredient ingredient2 = new IngredientConfirmed("Pepper", 0, MeasurementType.GRAM);
        ingredientService.add(ingredient1);
        ingredientService.add(ingredient2);

        List<? extends Ingredient> ingredients = ingredientService.getAllSorted(Comparator.comparing(Ingredient::getName));
        assertEquals(2, ingredients.size());
        assertEquals(ingredient2, ingredients.get(0));
        assertEquals(ingredient1, ingredients.get(1));
        ingredientService.clear();

    }

    @Test
    public void testRemove() {
        Ingredient ingredient = new IngredientConfirmed("Salt", 0, MeasurementType.GRAM);
        ingredientService.add(ingredient);
        boolean removed = ingredientService.remove(ingredient);

        assertTrue(removed);
        assertNull(ingredientRepository.findById(ingredient.getId()).orElse(null));
        ingredientService.clear();

    }

    @Test
    public void testGet() {
        Ingredient ingredient = new IngredientConfirmed("Salt", 0, MeasurementType.GRAM);
        ingredientService.add(ingredient);

        Ingredient retrievedIngredient = ingredientService.get(ingredient.getId());
        assertNotNull(retrievedIngredient);
        assertEquals(ingredient, retrievedIngredient);
        ingredientService.clear();

    }

    @Test
    public void testRemoveById() {
        Ingredient ingredient = new IngredientConfirmed("Salt", 0, MeasurementType.GRAM);
        ingredientService.add(ingredient);
        Ingredient removedIngredient = ingredientService.remove(ingredient.getId());

        assertNotNull(removedIngredient);
        assertEquals(ingredient, removedIngredient);
        assertNull(ingredientRepository.findById(ingredient.getId()).orElse(null));
        ingredientService.clear();

    }

    @Test
    public void testContains() {
        Ingredient ingredient = new IngredientConfirmed("Salt", 0, MeasurementType.GRAM);
        ingredientService.add(ingredient);
        boolean contains = ingredientService.contains(ingredient);

        assertTrue(contains);
        ingredientService.clear();

    }


}
