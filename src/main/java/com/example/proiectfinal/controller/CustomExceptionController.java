/**@author Mihaita Hingan*/
package com.example.proiectfinal.controller;

import com.example.proiectfinal.exception.CustomValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class CustomExceptionController {
    /**
     * Exception handler for handling CustomValidationException.
     * @param ex The CustomValidationException instance.
     * @return A ModelAndView with error details.
     */
    @ExceptionHandler(CustomValidationException.class)
    public ModelAndView handleCustomValidationException(CustomValidationException ex) {
        ModelAndView modelAndView = new ModelAndView("admin/error/error");
        modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        modelAndView.addObject("errorCode", HttpStatus.BAD_REQUEST.value());
        modelAndView.addObject("errorMessage", ex.getMessage());
        return modelAndView;
    }
}
