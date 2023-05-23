package com.studyroom.studyroomapp.controller.errors.advices;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.studyroom.studyroomapp.controller.errors.ErrorMessage;
import com.studyroom.studyroomapp.controller.errors.exceptions.ReservasExceptions.FechaAnteriorException;
import com.studyroom.studyroomapp.controller.errors.exceptions.ReservasExceptions.FormatoFechaException;

@ControllerAdvice
public class ReservaControllerAdvice {
    
    @ResponseBody
    @ExceptionHandler(FormatoFechaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorMessage formatoFecha(FormatoFechaException ex){
        return new ErrorMessage(400, ex.getMessage(), "La fecha no cumple el formato");
    }

    @ResponseBody
    @ExceptionHandler(FechaAnteriorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorMessage fechaAnterior(FechaAnteriorException ex){
        return new ErrorMessage(400, ex.getMessage(), "La fecha es anterior a la actual");
    }
}
