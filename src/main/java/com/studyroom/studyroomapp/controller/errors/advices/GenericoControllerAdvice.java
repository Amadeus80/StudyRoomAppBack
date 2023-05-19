package com.studyroom.studyroomapp.controller.errors.advices;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.studyroom.studyroomapp.controller.errors.ErrorMessage;
import com.studyroom.studyroomapp.controller.errors.exceptions.Genericas.DuplicadoException;
import com.studyroom.studyroomapp.controller.errors.exceptions.Genericas.NotFoundException;

@ControllerAdvice
public class GenericoControllerAdvice {
    
    @ResponseBody
    @ExceptionHandler(DuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ErrorMessage emailRepetido(DuplicadoException ex){
        return new ErrorMessage(409, ex.getMessage(), "Duplicado");
    }

    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorMessage usernameRepetido(NotFoundException ex){
        return new ErrorMessage(404, ex.getMessage(), "Not found");
    }
}
