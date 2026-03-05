package com.example.galactic_exploration.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ModelAndView handleConflict(DataIntegrityViolationException e) {
        System.err.println("DataIntegrityViolationException: " + e.getMessage());
        ModelAndView mav = new ModelAndView();
        mav.addObject("error", "Data integrity violation: This record might already exist (e.g. duplicate planet name).");
        mav.setViewName("error");
        return mav;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleAll(Exception e) {
        System.err.println("Exception caught in GlobalExceptionHandler: " + e.getClass().getName() + ": " + e.getMessage());
        e.printStackTrace(); // Log to console for background monitoring
        
        ModelAndView mav = new ModelAndView();
        mav.addObject("error", "Internal Server Error (" + e.getClass().getSimpleName() + "): " + e.getMessage());
        mav.setViewName("error");
        return mav;
    }
}
