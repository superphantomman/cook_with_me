package com.superphantomman.cook_with_me.sections.recipe;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/recipes")
public class RecipeController {

    @GetMapping
    public ModelAndView recipes() {
        final var mav = new ModelAndView("/recipe/recipes");
        return mav;
    }

    @PostMapping
    public ModelAndView recipes(String search) {
        final var mav = new ModelAndView("/recipe/recipes");
        return mav;
    }

    @GetMapping("/create")
    public ModelAndView createRecipe() {
        final var mav = new ModelAndView("/recipe/create");
        return mav;
    }

    @PostMapping("/create")
    public ModelAndView createRecipe(String name) {
        final var mav = new ModelAndView("/recipe/create");
        return mav;
    }

    @GetMapping("/details")
    public ModelAndView detailsRecipe(@RequestParam("id") Long id) {
        final var mav = new ModelAndView("recipe/details");
        return mav;
    }


    @GetMapping("/create/success")
    public ModelAndView successRecipe() {
        final var mav = new ModelAndView("/success/success");
        return mav;
    }


}
