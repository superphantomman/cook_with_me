package com.superphantomman.cook_with_me.sections.ingredient;

import com.superphantomman.cook_with_me.exceptions.NotFoundEntityException;
import com.superphantomman.cook_with_me.exceptions.NotPersistedEntityException;
import com.superphantomman.cook_with_me.sections.ingredient.models.entities.Ingredient;
import com.superphantomman.cook_with_me.sections.ingredient.models.forms.IngredientForm;
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
@RequestMapping("/ingredients")
@Slf4j
@AllArgsConstructor
final public class IngredientController {

    private final IngredientDaoService daoService;
    private ControllerUtil controllerUtil;

    @GetMapping()
    public ModelAndView ingredients() {

        final var mav = new ModelAndView("/ingredient/ingredients");
        mav.addObject("ingredients", daoService.getAll());
        log.info("GET request received on path /ingredients");

        return mav;
    }

    @GetMapping("/{search}")
    public ModelAndView ingredients(@PathVariable(value = "search", required = false) String search) {

        final var mav = new ModelAndView("/ingredient/ingredients");

        if (search.equals(""))
            mav.addObject("ingredients", daoService.getAll());

        mav.addObject("ingredients", daoService.getAll(search));

        log.info("POST request received on path /ingredients?search=" + search);

        return mav;
    }

    @GetMapping("/create")
    public ModelAndView createIngredient() {

        final var mav = new ModelAndView("/ingredient/create");
        mav.addObject("form", new IngredientForm());
        log.info("GET request received on path /ingredients/create");

        return mav;
    }

    @PostMapping("/create")
    public ModelAndView createIngredient(
            @ModelAttribute IngredientForm form, Errors errors,
        RedirectAttributes attributes
    ) {

        log.info("POST request received on path /ingredients/create");
        final ModelAndView mav;
        if (errors.hasErrors()) {
            mav = new ModelAndView("/ingredient/create");
            mav.addObject("form", form);
            return mav;
        }

        Ingredient i = form.toEntity();
        if (!daoService.add(i)) {
            throw new NotPersistedEntityException("Not persisted ingredient object from /ingredient/create");
        }

        log.info("Persisted ingredient entity with id: {}", i.getId());

        mav = new ModelAndView((new RedirectView("/create/success", true, false)));
        attributes.addAttribute("ingredientId", i.getId());


        return mav;

    }


    @GetMapping("/create/success")
    public ModelAndView success(@RequestParam("ingredientId")  Long ingredientId) {
        final ModelAndView mav = new ModelAndView("/success/success");
        return controllerUtil.success(
                mav, "ingredients/create", daoService.get(ingredientId), ingredientId );
    }


    @GetMapping("/details/{id}")
    public ModelAndView detailsIngredient(@PathVariable("id") Long id) {
        final Ingredient ingredient = daoService.get(id);

        if (ingredient == null) {

            throw new NotFoundEntityException("Not founded ingredient with id = " + id + " recipes/details/");
        }

        final var mav = new ModelAndView("ingredient/details");
        mav.addObject("ingredient", ingredient);


        return mav;
    }


}
