package com.studyroom.studyroomapp.controller.errors.exceptions.Genericas;

public class DuplicadoException extends RuntimeException{
    
    public DuplicadoException(String entidad){
        super(entidad.concat(" está repetido/a"));
    }
}
