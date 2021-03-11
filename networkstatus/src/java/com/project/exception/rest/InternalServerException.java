package com.project.exception.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Samrit
 */
@ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerException extends RuntimeException {
    public InternalServerException() {
    }

    public InternalServerException(Throwable cause) {
        super(cause);
    }
}
