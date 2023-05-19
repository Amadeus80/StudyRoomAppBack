package com.studyroom.studyroomapp.controller.errors.advices;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.studyroom.studyroomapp.controller.errors.ErrorMessage;
import com.studyroom.studyroomapp.controller.errors.exceptions.UsuarioExceptions.UsuarioEmailRepetidoException;
import com.studyroom.studyroomapp.controller.errors.exceptions.UsuarioExceptions.UsuarioNombreRepetidoException;

@ControllerAdvice
public class UserControllerAdvice {
    
    @ResponseBody
    @ExceptionHandler(UsuarioEmailRepetidoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ErrorMessage emailRepetido(UsuarioEmailRepetidoException ex){
        return new ErrorMessage(409, ex.getMessage(), "El email no puede ser repetido");
    }

    @ResponseBody
    @ExceptionHandler(UsuarioNombreRepetidoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ErrorMessage usernameRepetido(UsuarioNombreRepetidoException ex){
        return new ErrorMessage(409, ex.getMessage(), "El username que se intento dar de alta ya existía en la base de datos");
    }
}
