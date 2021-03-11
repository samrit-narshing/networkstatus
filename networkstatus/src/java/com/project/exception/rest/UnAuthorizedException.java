package com.project.exception.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Samrit
 */
@ResponseStatus(value= HttpStatus.UNAUTHORIZED)
public class UnAuthorizedException extends RuntimeException {
    public UnAuthorizedException() {
    }

    public UnAuthorizedException(Throwable cause) {
        super(cause);
    }
}
