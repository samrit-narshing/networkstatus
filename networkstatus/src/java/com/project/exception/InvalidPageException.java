/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.exception;

/**
 *
 * @author Samrit
 */
public class InvalidPageException extends Exception {

    //Parameterless Constructor

    public InvalidPageException() {
    }

    //Constructor that accepts a message
    public InvalidPageException(String message) {
        super(message);
    }
}
