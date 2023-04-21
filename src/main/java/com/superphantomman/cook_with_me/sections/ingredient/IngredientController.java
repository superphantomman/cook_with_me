package com.superphantomman.cook_with_me.sections.ingredient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {
    @GetMapping
    public ModelAndView ingredients(){
        final var mav = new ModelAndView("/ingredient/ingredients");
        return mav;
    }

    @PostMapping
    public ModelAndView ingredients(String search){
        final var mav = new ModelAndView("/ingredient/ingredients");
        return mav;
    }

    @GetMapping("/details")
    public ModelAndView detailsIngredient(@RequestParam("id") Long id) {
        final var mav = new ModelAndView("ingredient/details");
        return mav;
    }
    @GetMapping("/create")
    public ModelAndView createIngredient(){
        final var mav = new ModelAndView("/ingredient/ingredients");
        return mav;
    }

    @PostMapping("/create")
    public ModelAndView createIngredient( String ingredient ){
        final var mav = new ModelAndView("/ingredient/ingredients");
        return mav;
    }






}
