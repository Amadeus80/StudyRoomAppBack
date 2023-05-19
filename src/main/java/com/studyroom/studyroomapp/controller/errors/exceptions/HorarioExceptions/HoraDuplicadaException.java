package com.studyroom.studyroomapp.controller.errors.exceptions.HorarioExceptions;

public class HoraDuplicadaException extends RuntimeException{
    
    public HoraDuplicadaException(String hora){
        super("La hora: ".concat(hora).concat(" ya se encuentra en la base de datos"));
    }
}
