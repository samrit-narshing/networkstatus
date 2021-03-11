/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.exception.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

}
