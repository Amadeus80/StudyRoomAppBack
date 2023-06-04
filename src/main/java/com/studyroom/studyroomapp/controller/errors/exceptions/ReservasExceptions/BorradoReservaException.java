package com.studyroom.studyroomapp.controller.errors.exceptions.ReservasExceptions;

public class BorradoReservaException extends RuntimeException{

    public BorradoReservaException(String reserva){
        super("La reserva: ".concat(reserva).concat(" no pertenece al usuario logeado."));
    }
    
}
