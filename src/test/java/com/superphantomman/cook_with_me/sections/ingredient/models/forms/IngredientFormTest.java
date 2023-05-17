package com.superphantomman.cook_with_me.sections.ingredient.models.forms;

import com.superphantomman.cook_with_me.sections.ingredient.IngredientDaoService;
import com.superphantomman.cook_with_me.sections.ingredient.models.entities.Ingredient;
import com.superphantomman.cook_with_me.sections.ingredient.models.entities.IngredientConfirmed;
import com.superphantomman.cook_with_me.sections.ingredient.models.entities.IngredientUnconfirmed;
import com.superphantomman.cook_with_me.util.Entities;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static com.superphantomman.cook_with_me.util.Entities.compareEntities;
import static com.superphantomman.cook_with_me.util.MeasurementType.GRAM;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
public class IngredientFormTest {
    @Autowired
    private IngredientDaoService daoService;

    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private Validator validator = factory.getValidator();

    private Set<ConstraintViolation<IngredientForm>> violations;


    @Autowired
    public IngredientFormTest(IngredientDaoService daoService) {
        this.daoService = daoService;
    }


    @Test
    void testToEntity() {
        IngredientForm form = new IngredientForm();
        Ingredient i = new IngredientUnconfirmed("apple", 100, GRAM);
        form.setName("apple");
        form.setCalories(100);
        form.setMt(GRAM);
        assertTrue(
                compareEntities(i, form.toEntity())
        );
        form.setName("banana");
        assertFalse(
                compareEntities(i, form.toEntity())
        );
        form.setName("apple");

        i = new IngredientConfirmed(i);
        assertFalse(
                compareEntities(i, form.toEntity())
        );


    }

    @Test
    void testConstraints() {

        //Null name
        IngredientForm form = new IngredientForm();
        form.setCalories(100);
        form.setMt(GRAM);
        violations = validator.validate(form);
        assertFalse(violations.isEmpty());
        form.setName("apple");


        //Null calories
        form.setCalories(null);
        violations = validator.validate(form);
        assertFalse(violations.isEmpty());
        form.setCalories(100);

        //Null measurementType

        form.setMt(null);
        violations = validator.validate(form);
        assertFalse(violations.isEmpty());
        form.setMt(GRAM);


        //All values present
        violations = validator.validate(form);
        assertTrue(violations.isEmpty());

        // Unacceptable sign is present in name
        form.setName("@pple");
        violations = validator.validate(form);
        assertFalse(violations.isEmpty());
        form.setName("apple");

        // Unacceptable value is present in calories

        form.setCalories(-1);
        violations = validator.validate(form);
        assertFalse(violations.isEmpty());
    }


    @Test
    public void testAdd() {
        IngredientForm form = new IngredientForm();
        form.setName("apple");
        form.setCalories(100);
        form.setMt(GRAM);

        assertTrue(daoService.add(form));
        Ingredient i = daoService.get(form.getName());
        assertNotEquals(i, null);
        assertTrue(
                Entities.compareEntities(
                    i, form.toEntity()
                )
        );


    }

}
