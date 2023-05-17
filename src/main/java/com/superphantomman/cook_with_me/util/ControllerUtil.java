package com.superphantomman.cook_with_me.util;

import com.superphantomman.cook_with_me.sections.ingredient.models.entities.Ingredient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;


@Slf4j
@Component
public class ControllerUtil {
    public <T> ModelAndView success(
            ModelAndView mav, String viewName, T entity, Long id
    ) {
        final SuccessObject successObj = new SuccessObject(entity);

        mav.addObject("successObj", successObj);
        final String idName = entity.getClass().getSimpleName() + "Id";

        log.info("POST request received on path {}/success?{}={}", viewName, idName , id);

        return mav;
    }
}
