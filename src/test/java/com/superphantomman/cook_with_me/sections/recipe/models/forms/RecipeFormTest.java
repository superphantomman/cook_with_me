package com.superphantomman.cook_with_me.sections.recipe.models.forms;


import com.superphantomman.cook_with_me.exceptions.FormConversionException;
import com.superphantomman.cook_with_me.exceptions.NotFoundEntityException;
import com.superphantomman.cook_with_me.sections.ingredient.IngredientDaoService;
import com.superphantomman.cook_with_me.sections.ingredient.models.entities.IngredientConfirmed;
import com.superphantomman.cook_with_me.sections.ingredient.models.forms.IngredientRecipeForm;
import com.superphantomman.cook_with_me.sections.recipe.services.RecipeDaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static com.superphantomman.cook_with_me.util.MeasurementType.GRAM;
import static org.junit.jupiter.api.Assertions.*;


@Transactional
@SpringBootTest
public class RecipeFormTest {

    @Autowired
    RecipeDaoService recipeDaoService;

    @Autowired
    IngredientDaoService ingredientDaoService;

    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private Validator validator = factory.getValidator();

    private Set<ConstraintViolation<RecipeForm>> violations;

    @Autowired
    public RecipeFormTest(RecipeDaoService recipeDaoService, IngredientDaoService ingredientDaoService) {
        this.recipeDaoService = recipeDaoService;
        this.ingredientDaoService = ingredientDaoService;
    }

    @Test
    void testToEntity() {
        var form = new RecipeForm();
        var irForm = new IngredientRecipeForm();

        irForm.setIngredientName("apple");
        irForm.setWeight(100f);
        irForm.setMt(GRAM);

        form.setDescription("Cutting apple");
        assertTrue(form.addIngredient(irForm));

        //daoService not provided in order to validate ingredientName present in database
        assertThrows(FormConversionException.class, form::toEntity);

        form.setDaoService(ingredientDaoService);

        //database does not contain ingredient with given name
        assertThrows(NotFoundEntityException.class, form::toEntity);

        ingredientDaoService.add(
                new IngredientConfirmed("apple", 100, GRAM)
        );

        assertDoesNotThrow(form::toEntity);

    }


    @Test
    void testConstraints() {
        var form = new RecipeForm();
        var irForm = new IngredientRecipeForm();

        //All values present

        irForm.setIngredientName("apple");
        irForm.setWeight(100f);
        irForm.setMt(GRAM);

        form.setDescription("Cutting apple");
        assertTrue(form.addIngredient(irForm));

        violations = validator.validate(form);
        assertTrue(violations.isEmpty());


        //Null as a description
        form.setDescription(null);
        violations = validator.validate(form);
        assertFalse(violations.isEmpty());







    }
}