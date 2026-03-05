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
        ModelAndView mav = new ModelAndView();
        mav.addObject("error", "Data integrity violation: This record might already exist (e.g. duplicate planet name).");
        mav.setViewName("error");
        return mav;
    }
}
