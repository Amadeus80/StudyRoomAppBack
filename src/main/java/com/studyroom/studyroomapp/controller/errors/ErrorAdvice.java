package com.studyroom.studyroomapp.controller.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.studyroom.studyroomapp.controller.errors.exceptions.UsuarioEmailRepetidoException;

@ControllerAdvice
public class ErrorAdvice {
    
    @ResponseBody
    @ExceptionHandler(UsuarioEmailRepetidoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorMessage emailRepetido(UsuarioEmailRepetidoException ex){
        return new ErrorMessage(404, ex.getMessage(), "El email no puede ser repetido");
    }
}
