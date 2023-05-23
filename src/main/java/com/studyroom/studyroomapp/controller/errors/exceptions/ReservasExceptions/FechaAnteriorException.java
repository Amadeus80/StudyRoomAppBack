package com.studyroom.studyroomapp.controller.errors.exceptions.ReservasExceptions;

import java.util.Date;

public class FechaAnteriorException extends RuntimeException{

    public FechaAnteriorException(String fecha){
        super("La fecha: ".concat(fecha).concat(" es anterior a la actual ".concat(new Date().toString())));
    }
    
}
