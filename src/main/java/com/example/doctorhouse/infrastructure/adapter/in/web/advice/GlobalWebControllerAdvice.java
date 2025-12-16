package com.example.doctorhouse.infrastructure.adapter.in.web.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "com.example.doctorhouse.infrastructure.adapter.in.web")
public class GlobalWebControllerAdvice {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        model.addAttribute("errorMessage", "Ha ocurrido un error inesperado: " + ex.getMessage());
        return "error";
    }
}
