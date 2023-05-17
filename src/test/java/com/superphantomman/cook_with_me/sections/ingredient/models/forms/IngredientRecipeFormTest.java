package com.superphantomman.cook_with_me.sections.ingredient.models.forms;

import com.superphantomman.cook_with_me.exceptions.FormConversionException;
import org.junit.jupiter.api.Test;
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
public class IngredientRecipeFormTest {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    Set<ConstraintViolation<IngredientRecipeForm>> violations;



    @Test
    void testIncompleteToEntity(){
        var form = new IngredientRecipeForm();
        form.setIngredientName("apple");
        form.setWeight(100f);
        form.setMt(GRAM);

        /* FormConversionException is present because
           form doesn't contain reference neither to recipe and ingredient
        * */
        assertThrows( FormConversionException.class, form::toEntity );
    }
    @Test
    void testConstraints() {
        var form = new IngredientRecipeForm();


        //All values present
        form.setIngredientName("apple");
        form.setWeight(100f);
        form.setMt(GRAM);
        violations = validator.validate(form);
        assertTrue(violations.isEmpty());

        //Null as a ingredientName
        form.setIngredientName(null);
        violations = validator.validate(form);
        assertFalse(violations.isEmpty());
        form.setIngredientName("apple");

        //Null as a weight
        form.setWeight(null);
        violations = validator.validate(form);
        assertFalse(violations.isEmpty());
        form.setWeight(100f);

        //Null measurementType
        form.setMt(null);
        violations = validator.validate(form);
        assertFalse(violations.isEmpty());

        //Invalid sign present in ingredientName
        form.setIngredientName("@pple");
        violations = validator.validate(form);
        assertFalse(violations.isEmpty());
        form.setIngredientName("apple");

        //Invalid negative value present in weight
        form.setWeight(-1f);
        violations = validator.validate(form);
        assertFalse(violations.isEmpty());
        form.setWeight(100f);


    }



}
