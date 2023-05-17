package com.superphantomman.cook_with_me.sections.ingredient;


import com.superphantomman.cook_with_me.sections.ingredient.models.entities.Ingredient;
import com.superphantomman.cook_with_me.sections.ingredient.models.entities.IngredientConfirmed;
import com.superphantomman.cook_with_me.sections.ingredient.models.entities.IngredientUnconfirmed;
import com.superphantomman.cook_with_me.sections.ingredient.models.forms.IngredientForm;
import com.superphantomman.cook_with_me.util.ControllerUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.superphantomman.cook_with_me.util.MeasurementType.GRAM;
import static com.superphantomman.cook_with_me.util.MeasurementType.LITER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(IngredientController.class)
public class IngredientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private IngredientController ingredientController;

    @MockBean
    private IngredientDaoService daoService;

    @MockBean
    private ControllerUtil controllerUtil;

    @Test
    public void testIngredients() throws Exception {
        List<Ingredient> mockIngredients = List.of(
                new IngredientConfirmed("apple", 30, GRAM),
                new IngredientConfirmed("banana", 100, GRAM),
                new IngredientConfirmed("milk", 500, LITER)
        );

        when(daoService.getAll()).thenReturn(mockIngredients);

        mockMvc.perform(get("/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("/ingredient/ingredients"))
                .andExpect(model().attribute("ingredients", mockIngredients));
    }

    @Test
    public void testIngredientsSearch() throws Exception {
        List<Ingredient> mockIngredients = List.of(
                new IngredientConfirmed("apple", 30, GRAM),
                new IngredientConfirmed("green apple", 30, GRAM)
        );

        final String search = "apple";
        when(daoService.getAll(search)).thenReturn(mockIngredients);

        mockMvc.perform(get("/ingredients/" + search))
                .andExpect(status().isOk())
                .andExpect(view().name("/ingredient/ingredients"))
                .andExpect(model().attribute("ingredients", mockIngredients));

    }

    @Test
    public void testCreateIngredientGET() throws Exception {

        final IngredientForm mockForm = new IngredientForm();

        mockMvc.perform(get("/ingredients/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("/ingredient/create"))
                .andExpect(model().attribute("form", mockForm));


    }

    @Test
    public void testCreateIngredientPOST() throws Exception {

        final IngredientForm mockForm = new IngredientForm();

        mockForm.setName("apple");
        mockForm.setCalories(20);
        mockForm.setMt(GRAM);

        Ingredient mockIngredient = mockForm.toEntity();
        var mockModelAndView = new ModelAndView("/success/success");
        mockModelAndView.addObject("ingredientId", 1L);

        when(daoService.add(any(Ingredient.class))).thenReturn(true);
        when(daoService.get(1L)).thenReturn(mockIngredient);


        mockMvc.perform(post("/ingredients/create"))
                .andExpect(status().isSeeOther())
                .andExpect(redirectedUrl("/create/success"));
    }

    @Test
    public void testSuccess() throws Exception {
        Long ingredientId = 123L;

        Ingredient mockIngredient = new IngredientUnconfirmed();
        ModelAndView mockMav = new ModelAndView("/success/success");
        mockIngredient.setId(ingredientId);

        when(controllerUtil
                .success(any(ModelAndView.class), any(String.class),
                        any(Ingredient.class), any(Long.class) ) )
                .thenReturn(mockMav);
        when(daoService.get( any(Long.class)) ).thenReturn(mockIngredient);

        mockMvc.perform(get("/ingredients/create/success?ingredientId="+ingredientId)
                        .param("ingredientId", String.valueOf(ingredientId))
                        .flashAttr("ingredient", mockIngredient))
                .andExpect(status().isOk())
                .andExpect(view().name("/success/success"));

    }


    @Test
    public void testDetailIngredient() throws Exception {

        Ingredient mockIngredient = new IngredientConfirmed("Apple", 20, GRAM);


        when(daoService.get(1L)).thenReturn(mockIngredient);

        mockMvc.perform(get("/ingredients/details/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("ingredient/details"))
                .andExpect(model().attribute("ingredient", mockIngredient));

    }



}
