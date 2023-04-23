package com.superphantomman.cook_with_me.exceptions;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
@Slf4j

@ControllerAdvice
public class ApiExceptionHandler {




    @ExceptionHandler(NotFoundEntityException.class)
    @ResponseStatus(NOT_FOUND)
    public ModelAndView handleNotFoundEntityException(
            NotFoundEntityException e
    ) {
        final var mav = new ModelAndView("/errors/error");

        final var apiExceptionInformation = new ApiExceptionInformation(
                e,
                NOT_FOUND,
                ZonedDateTime.now()
        );
        log.error("Entity not founded: {}", e.getMessage());

        mav.addObject("apiExceptionInformation", apiExceptionInformation);

        return mav;


    }
    @ExceptionHandler(NotPersistedEntityException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)

    public ModelAndView handleNotFoundEntityException(
            NotPersistedEntityException e
    ){
        final var mav = new ModelAndView("/errors/error");

        final var apiExceptionInformation = new ApiExceptionInformation(
                e,
                INTERNAL_SERVER_ERROR,
                ZonedDateTime.now()
        );
        log.error("Entity not persisted: {}", e.getMessage());
        mav.addObject("apiExceptionInformation", apiExceptionInformation);

        return mav;

    }
    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView noHandlerFoundException(HttpServletRequest request, Exception e){

        final var mav = new ModelAndView("/errors/error");

        final var apiExceptionInformation = new ApiExceptionInformation(
                e,
                NOT_FOUND,
                ZonedDateTime.now()
        );

        log.error("No Handler is found : {}", e.getMessage());
        mav.addObject("apiExceptionInformation", apiExceptionInformation);


        return mav;
    }


}
