package com.superphantomman.cook_with_me.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotFoundEntityException extends RuntimeException{
    public NotFoundEntityException(String message) {
        super(message);
    }
}
