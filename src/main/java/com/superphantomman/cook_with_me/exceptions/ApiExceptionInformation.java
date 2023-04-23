package com.superphantomman.cook_with_me.exceptions;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;


public record ApiExceptionInformation(Exception exception, HttpStatus httpStatus, ZonedDateTime zonedDateTime) {
}
