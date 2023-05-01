package com.superphantomman.cook_with_me.sections.recipe;


import com.superphantomman.cook_with_me.exceptions.NotFoundEntityException;
import com.superphantomman.cook_with_me.exceptions.NotPersistedEntityException;
import com.superphantomman.cook_with_me.sections.recipe.models.entities.Recipe;
import com.superphantomman.cook_with_me.sections.recipe.models.entities.RecipeInformation;
import com.superphantomman.cook_with_me.sections.recipe.models.entities.RecipeInformationPrivate;
import com.superphantomman.cook_with_me.sections.recipe.models.entities.RecipeInformationUnconfirmed;
import com.superphantomman.cook_with_me.sections.recipe.services.RecipeDaoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@AllArgsConstructor
@Slf4j
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeDaoService recipeDaoService;

    @GetMapping
    public ModelAndView recipes() {

        final var mav = new ModelAndView("/recipe/recipes");
        mav.addObject("recipes", recipeDaoService.getAll() );
        log.info("GET request received on path /recipes");

        return mav;
    }

    //If user search for exact recipe by name
    @PostMapping
    public ModelAndView recipes(@RequestParam(value = "search", defaultValue = "") String search) {

        final var mav = new ModelAndView("/recipe/recipes");
        if(!search.equals(""))
            mav.addObject("recipes", recipeDaoService.getAll(search));
                else
            mav.addObject("recipes", recipeDaoService.getAll());

        log.info("POST request received on path /recipes?search=" + search);

        return mav;
    }

    @GetMapping("/create")
    public ModelAndView createRecipe() {
        final var mav = new ModelAndView("/recipe/create");
        mav.addObject("recipe", new Recipe());
        mav.addObject("recipe_information", new RecipeInformationPrivate());
        mav.addObject("isPublic", false );
        log.info("GET request received on path /recipes/create");
        return mav;
    }

    @PostMapping("/create")
    public RedirectView createRecipe(
            @ModelAttribute Recipe r, @ModelAttribute RecipeInformation ri,
            @ModelAttribute Boolean isPublic,
            RedirectAttributes attributes) {

        RecipeInformation recipeInformation = ri;
        if (isPublic){
            recipeInformation =  new RecipeInformationUnconfirmed(ri) ;
        }

        if(!recipeDaoService.add(r, recipeInformation )){
            throw new NotPersistedEntityException();
        }

        attributes.addAttribute("recipeInformation", ri);
        log.info("POST request received on path /recipes/create");

        return new RedirectView("/success", true);
    }

    @GetMapping("/create/success")
    public ModelAndView successRecipe( @RequestParam("recipe") Long recipeId ) {

        final var mav = new ModelAndView("/success/success");
        mav.addObject("recipe", recipeDaoService.get(recipeId));
        log.info("POST request received on path recipes/create/success?recipeId=" + recipeId);

        return mav;
    }

    @GetMapping("/details/{id}")
    public ModelAndView detailsRecipe(@PathVariable("id") Long id) {

        final Recipe recipe = recipeDaoService.get(id);
        System.out.println(recipe);

        if( recipe == null) {
            throw new NotFoundEntityException("Not founded recipe with id = " + id + " recipes/details/");
        }

        final var mav = new ModelAndView("recipe/details");
        mav.addObject("recipe", recipeDaoService.get(id));
        log.info("POST request received on path /recipes/details/"+id);

        return mav;
    }



}
