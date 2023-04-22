package com.superphantomman.cook_with_me.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotPersistedEntityException extends RuntimeException{
    public NotPersistedEntityException(String message) {
        super(message);
    }
}
