package com.superphantomman.cook_with_me.sections.recipe.models.forms;

import com.superphantomman.cook_with_me.sections.recipe.models.entities.RecipeInformation;
import com.superphantomman.cook_with_me.sections.recipe.models.entities.RecipeInformationUnconfirmed;
import com.superphantomman.cook_with_me.sections.recipe.services.RecipeDaoService;
import com.superphantomman.cook_with_me.sections.recipe.services.RecipeInformationDaoService;
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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@Transactional
@SpringBootTest
public class RecipeInformationFormTest {

    @Autowired
    private final RecipeDaoService recipeDaoService;

    @Autowired
    private final RecipeInformationDaoService informationDaoService;

    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private Validator validator = factory.getValidator();

    private Set<ConstraintViolation<RecipeInformationForm>> violations;

    @Autowired
    public RecipeInformationFormTest(RecipeDaoService recipeDaoService, RecipeInformationDaoService informationDaoService) {
        this.recipeDaoService = recipeDaoService;
        this.informationDaoService = informationDaoService;
    }


    @Test
    public void testToEntity() {

        RecipeInformationForm form = new RecipeInformationForm();
        RecipeInformation information = new RecipeInformationUnconfirmed("sandwich");
        form.setName("sandwich");

        assertTrue(Entities.compareEntities(form.toEntity(), information));

        form.setName(null);

        assertFalse(Entities.compareEntities(form.toEntity(), information));

    }


    @Test
    public void testConstraints() {
        RecipeInformationForm form = new RecipeInformationForm();

        // Important Param are present
        form.setName("sandwich");
        violations = validator.validate(form);
        assertTrue(violations.isEmpty());

        //Null as a name
        form.setName(null);
        violations = validator.validate(form);
        assertFalse(violations.isEmpty());
        form.setName("sandwich");


        // Unacceptable sign is present in name
        form.setName("$andwich");
        violations = validator.validate(form);
        assertFalse(violations.isEmpty());
        form.setName("sandwich");


    }


}
