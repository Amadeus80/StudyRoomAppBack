package com.studyroom.studyroomapp.controller.errors.advices;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.studyroom.studyroomapp.controller.errors.ErrorMessage;
import com.studyroom.studyroomapp.controller.errors.exceptions.HorarioExceptions.HoraDuplicadaException;

@ControllerAdvice
public class HorarioControllerAdvice {

    @ResponseBody
    @ExceptionHandler(HoraDuplicadaException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ErrorMessage horaRepetida(HoraDuplicadaException ex){
        return new ErrorMessage(409, ex.getMessage(), "La hora esta repetida");
    }
}
