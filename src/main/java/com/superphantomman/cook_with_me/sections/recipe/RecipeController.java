package com.superphantomman.cook_with_me.sections.recipe;


import com.superphantomman.cook_with_me.sections.recipe.pojos.Recipe;
import com.superphantomman.cook_with_me.sections.recipe.pojos.RecipeInformation;
import com.superphantomman.cook_with_me.sections.recipe.pojos.RecipeInformationPrivate;
import com.superphantomman.cook_with_me.sections.recipe.pojos.RecipeInformationUnconfirmed;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@AllArgsConstructor
@RequestMapping("/recipes")
public class RecipeController {

    final RecipeService recipeService;

    @GetMapping
    public ModelAndView recipes() {
        final var mav = new ModelAndView("/recipe/recipes");
        mav.addObject("recipes", recipeService.getAll() );
        return mav;
    }


    //If user search for exact recipe by name
    @PostMapping
    public ModelAndView recipes(@RequestParam("search") String search) {
        final var mav = new ModelAndView("/recipe/recipes");
        mav.addObject("recipes", recipeService.getAll(search));
        return mav;
    }

    @GetMapping("/create")
    public ModelAndView createRecipe() {
        final var mav = new ModelAndView("/recipe/create");

        mav.addObject("recipe", new Recipe());
        mav.addObject("recipe_information", new RecipeInformationPrivate());
        mav.addObject("isPublic", false );

        return mav;
    }

    @PostMapping("/create")
    public RedirectView createRecipe(
            @ModelAttribute Recipe r, @ModelAttribute RecipeInformation ri,
            @ModelAttribute Boolean isPublic,
            RedirectAttributes attributes) {

        if (isPublic){
            recipeService.add(r, new RecipeInformationUnconfirmed(ri) );
        }else {
            recipeService.add(r, ri);
        }

        attributes.addAttribute("recipeInformation", ri);

        return new RedirectView("/success", true);
    }

    @GetMapping("/details/{id}")
    public ModelAndView detailsRecipe(@PathVariable("id") Long id) {
        final var mav = new ModelAndView("recipe/details");
        mav.addObject("recipe", recipeService.get(id));
        return mav;
    }


    @GetMapping("/create/success")
    public ModelAndView successRecipe( @RequestParam("recipe") Long recipeId ) {
        final var mav = new ModelAndView("/success/success");
        mav.addObject("recipe", recipeService.get(recipeId));
        return mav;
    }


}
