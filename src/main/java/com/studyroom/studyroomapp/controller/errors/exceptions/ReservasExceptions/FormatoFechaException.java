package com.studyroom.studyroomapp.controller.errors.exceptions.ReservasExceptions;

public class FormatoFechaException extends RuntimeException{
    public FormatoFechaException(String fecha){
        super("La fecha: ".concat(fecha).concat(" no cumple el formato"));
    }
}
