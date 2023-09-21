/**@author Mihaita Hingan*/
package com.example.proiectfinal.exception;

public class CustomValidationException extends RuntimeException{
    public CustomValidationException(String message) {
        super(message);
    }
}
