package com.superphantomman.cook_with_me.sections.ingredient;
import com.superphantomman.cook_with_me.exceptions.NotFoundEntityException;
import com.superphantomman.cook_with_me.sections.ingredient.pojos.Ingredient;
import com.superphantomman.cook_with_me.sections.ingredient.pojos.IngredientUnconfirmed;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/ingredients")
@Slf4j
@AllArgsConstructor
final public class IngredientController {

    private final IngredientService ingredientService;

    @GetMapping
    public ModelAndView ingredients(){

        final var mav = new ModelAndView("/ingredient/ingredients");
        mav.addObject("ingredients", ingredientService.getAll());
        log.info("GET request received on path /ingredients");

        return mav;
    }

    @PostMapping
    public ModelAndView ingredients(@RequestParam("search") String search){

        final var mav = new ModelAndView("/ingredient/ingredients");
        mav.addObject("ingredients", ingredientService.getAll(search));
        log.info("POST request received on path /ingredients?search=" + search);

        return mav;
    }

    @GetMapping("/create")
    public ModelAndView createIngredient(){

        final var mav = new ModelAndView("/ingredient/ingredients");
        mav.addObject("ingredient", new IngredientUnconfirmed());
        log.info("GET request received on path /ingredients/create");

        return mav;
    }

    @PostMapping("/create")
    public ModelAndView createIngredient( @ModelAttribute Ingredient ingredient ){

        if ( ingredientService.add(ingredient) ) {
            throw new NotFoundEntityException("Not persisted ingredient object from /ingredient/create");
        }

        final var mav = new ModelAndView("/ingredient/ingredients");
        log.info("POST request received on path /ingredients/create");

        return mav;
    }
    @GetMapping("/details/{id}")
    public ModelAndView detailsIngredient(@PathVariable("id") Long id) {
        final Ingredient ingredient = ingredientService.get(id);

        if( ingredient == null ){

            throw new NotFoundEntityException("Not founded ingredient with id = " + id + " recipes/details/");
        }

        final var mav = new ModelAndView("ingredient/details");
        mav.addObject("ingredient", ingredient);

        return mav;
    }



}
