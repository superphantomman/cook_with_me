package com.superphantomman.cook_with_me.sections.recipe;

import com.superphantomman.cook_with_me.sections.ingredient.IngredientDaoService;
import com.superphantomman.cook_with_me.sections.recipe.models.entities.Recipe;
import com.superphantomman.cook_with_me.sections.recipe.models.entities.RecipeInformation;
import com.superphantomman.cook_with_me.sections.recipe.models.entities.RecipeInformationConfirmed;
import com.superphantomman.cook_with_me.sections.recipe.models.entities.RecipeInformationUnconfirmed;
import com.superphantomman.cook_with_me.sections.recipe.models.forms.RecipeForm;
import com.superphantomman.cook_with_me.sections.recipe.models.forms.RecipeInformationForm;
import com.superphantomman.cook_with_me.sections.recipe.services.RecipeDaoService;
import com.superphantomman.cook_with_me.sections.recipe.services.RecipeInformationDaoService;
import com.superphantomman.cook_with_me.util.ControllerUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(RecipeController.class)
public class RecipeControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeDaoService recipeDaoService;

    @MockBean
    private IngredientDaoService ingredientDaoService;

    @MockBean
    private RecipeInformationDaoService informationDaoService;

    @MockBean
    private ControllerUtil controllerUtil;



    @Test
    public void testRecipes() throws Exception {
        final String search = "cake";

        List<RecipeInformation> mockInfos1 =
                List.of(
                        new RecipeInformationConfirmed("sandwich"),
                        new RecipeInformationConfirmed("cake"),
                        new RecipeInformationConfirmed("chocolate cake"),
                        new RecipeInformationConfirmed("soup")

                );

        List<RecipeInformation> mockInfos2 =
                List.of(
                        new RecipeInformationConfirmed("chocolate cake"),
                        new RecipeInformationConfirmed("cake")
                );



        when(informationDaoService.getAll()).thenReturn(mockInfos1);
        when(informationDaoService.getAll(search)).thenReturn(mockInfos2);

        mockMvc.perform(get("/recipes"))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/recipes"))
                .andExpect(model().attribute("recipeInfos", mockInfos1));

        mockMvc.perform(get("/recipes?search=" + search))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/recipes"))
                .andExpect(model().attribute("recipeInfos", mockInfos2));

    }

    @Test
    public void testCreateRecipeGET() throws Exception {
        RecipeForm recipeForm  = new RecipeForm();
        RecipeInformationForm recipeInfoForm = new RecipeInformationForm();

        mockMvc.perform(get("/recipes/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/create"))
                .andExpect(model().attribute( "recipeForm", recipeForm ))
                .andExpect(model().attribute( "recipeInfoForm", recipeInfoForm ));
    }

    @Test
    public void testCreateRecipePOST() throws Exception {
        Recipe mockRecipe = new Recipe("Cutting bread");
        RecipeInformation mockInfo = new RecipeInformationUnconfirmed("sandwich");

        RecipeForm recipeFormMock = mock(RecipeForm.class);
        RecipeInformationForm infoFormMock = mock(RecipeInformationForm.class);

        when(recipeFormMock.toEntity()).thenReturn(mockRecipe);
        when(infoFormMock.toEntity()).thenReturn(mockInfo);
        when(recipeDaoService.add(any(Recipe.class), any(RecipeInformation.class))).thenReturn(true);

        mockMvc.perform(post("/recipes/create"))
                .andExpect(status().isSeeOther())
                .andExpect(redirectedUrl("/create/success"));

    }

    @Test
    public void testSuccess() throws Exception {
        Long recipeInfoId = 123L;

        RecipeInformation mockRecipeInfo = new RecipeInformationUnconfirmed();
        ModelAndView mockMav = new ModelAndView("/success/success");
        mockRecipeInfo.setId(recipeInfoId);

        when(controllerUtil
                .success(any(ModelAndView.class), any(String.class),
                        any(RecipeInformation.class), any(Long.class) ) )
                .thenReturn(mockMav);
        when(informationDaoService.get( any(Long.class)) ).thenReturn(mockRecipeInfo);

        mockMvc.perform(get("/recipes/create/success?recipeInfoId="+recipeInfoId)
                        .param("recipeInfoId", String.valueOf(recipeInfoId))
                        .flashAttr("recipeInfo", mockRecipeInfo))
                .andExpect(status().isOk())
                .andExpect(view().name("/success/success"));

    }

    @Test
    public void testDetailIngredient() throws Exception {

        long recipeId = 123L;
        Recipe mockRecipe = new Recipe();

        when(recipeDaoService.get(any(Long.class))).thenReturn(mockRecipe);


        mockMvc.perform(get("/recipes/details/"+recipeId))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/details"))
                .andExpect(model().attribute("recipe", mockRecipe));

    }



}
