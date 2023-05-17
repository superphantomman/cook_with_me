package com.superphantomman.cook_with_me.sections.recipe;


import com.superphantomman.cook_with_me.exceptions.NotFoundEntityException;
import com.superphantomman.cook_with_me.exceptions.NotPersistedEntityException;
import com.superphantomman.cook_with_me.sections.ingredient.IngredientDaoService;
import com.superphantomman.cook_with_me.sections.recipe.models.entities.Recipe;
import com.superphantomman.cook_with_me.sections.recipe.models.entities.RecipeInformation;
import com.superphantomman.cook_with_me.sections.recipe.models.forms.RecipeForm;
import com.superphantomman.cook_with_me.sections.recipe.models.forms.RecipeInformationForm;
import com.superphantomman.cook_with_me.sections.recipe.services.RecipeDaoService;
import com.superphantomman.cook_with_me.sections.recipe.services.RecipeInformationDaoService;
import com.superphantomman.cook_with_me.util.ControllerUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@AllArgsConstructor
@Slf4j
@RequestMapping("/recipes")
public class RecipeController {

    private final ControllerUtil controllerUtil;
    private final RecipeDaoService recipeDaoService;
    private final IngredientDaoService ingredientDaoService;
    private final RecipeInformationDaoService informationDaoService;



    @GetMapping
    public ModelAndView recipes(@RequestParam(value = "search", defaultValue = "") String search) {

        final var mav = new ModelAndView("/recipe/recipes");
        if ( search.equals("") ) {
            mav.addObject("recipeInfos", informationDaoService.getAll());
        } else
            mav.addObject("recipeInfos", informationDaoService.getAll(search));

        log.info("POST request received on path /recipes?search=" + search);

        return mav;
    }

    @GetMapping("/create")
    public ModelAndView createRecipe() {
        final var mav = new ModelAndView("/recipe/create");
        mav.addObject("recipeForm", new RecipeForm());
        mav.addObject("recipeInfoForm", new RecipeInformationForm());
        log.info("GET request received on path /recipes/create");
        return mav;
    }

    @PostMapping("/create")
    public ModelAndView createRecipe(
            @ModelAttribute RecipeForm rf, @ModelAttribute RecipeInformationForm rif,
            RedirectAttributes attributes, Errors errors
           ) {

        final ModelAndView mav;

        if(errors.hasErrors()){
            mav = new ModelAndView("/recipe/create");
            mav.addObject("recipeForm", rf);
            mav.addObject("recipeInfoForm", rif);
            return mav;
        }

        rf.setDaoService(ingredientDaoService);
        Recipe r = rf.toEntity();
        RecipeInformation ri = rif.toEntity();

        if (!recipeDaoService.add(r, ri) ) {
            throw new NotPersistedEntityException("Not persisted recipe object from /recipes/create ");
        }


        log.info("POST request received on path /recipes/create");

        mav = new ModelAndView((new RedirectView("/create/success", true, false)));
        attributes.addAttribute("recipeInfoId", ri );

        return mav;
    }

    @GetMapping("/create/success")
    public ModelAndView successRecipe(@RequestParam("recipeInfoId") Long recipeInformationId) {
        final ModelAndView mav = new ModelAndView("/success/success");

        return controllerUtil.success(
                mav, "recipes/create", informationDaoService.get(recipeInformationId), recipeInformationId );
    }

    @GetMapping("/details/{id}")
    public ModelAndView detailsRecipe(@PathVariable("id") Long id) {

        final Recipe recipe = recipeDaoService.get(id);

        if (recipe == null) {
            throw new NotFoundEntityException("Not founded recipe with id = " + id + " recipes/details/");
        }

        final var mav = new ModelAndView("recipe/details");
        mav.addObject("recipe", recipeDaoService.get(id));
        log.info("POST request received on path /recipes/details/" + id);

        return mav;
    }


}
