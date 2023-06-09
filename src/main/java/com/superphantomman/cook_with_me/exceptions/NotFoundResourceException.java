package com.superphantomman.cook_with_me.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundResourceException extends RuntimeException{
    public NotFoundResourceException(String message) {
        super(message);
    }
}
