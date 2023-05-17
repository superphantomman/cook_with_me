package com.superphantomman.cook_with_me.sections.recipe.models;


import com.superphantomman.cook_with_me.sections.ingredient.IngredientController;
import com.superphantomman.cook_with_me.sections.ingredient.IngredientDaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(IngredientController.class)

public class IngredientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IngredientDaoService service;

    @Test
    public void testControllerMethod() throws Exception {


    }

}
