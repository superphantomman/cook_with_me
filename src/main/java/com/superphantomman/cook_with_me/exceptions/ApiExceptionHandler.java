package com.superphantomman.cook_with_me.exceptions;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
//TODO add Model and View
@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(NotFoundEntityException.class)
    @ResponseStatus(NOT_FOUND)
    public ResponseEntity<String> handleNotFoundEntityException(
            NotFoundEntityException e
    ) {

        final var apiExceptionInformation = new ApiExceptionInformation(
                e,
                NOT_FOUND,
                ZonedDateTime.now()
        );
        log.error("Entity not founded: {}", e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiExceptionInformation.toString());


    }

    @ExceptionHandler(NotPersistedEntityException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)

    public ResponseEntity<ApiExceptionInformation> handleNotFoundEntityException(
            NotPersistedEntityException e
    ){
        final var apiExceptionInformation = new ApiExceptionInformation(
                e,
                INTERNAL_SERVER_ERROR,
                ZonedDateTime.now()
        );
        log.error("Entity not persisted: {}", e.getMessage());

        return new ResponseEntity<>(apiExceptionInformation, INTERNAL_SERVER_ERROR);

    }
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiExceptionInformation> noHandlerFoundException(HttpServletRequest request, Exception e){
        final var apiExceptionInformation = new ApiExceptionInformation(
                e,
                NOT_FOUND,
                ZonedDateTime.now()
        );
        log.error("No Handler is found : {}",e.getMessage());

        return new ResponseEntity<>(apiExceptionInformation, NOT_FOUND);
    }


}
