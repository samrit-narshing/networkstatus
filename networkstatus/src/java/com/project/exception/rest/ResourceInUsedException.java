package com.project.exception.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Samrit
 */
@ResponseStatus(value = HttpStatus.IM_USED)
public class ResourceInUsedException extends RuntimeException {

    public ResourceInUsedException() {
    }

    public ResourceInUsedException(Throwable cause) {
        super(cause);
    }
}
