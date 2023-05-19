package com.studyroom.studyroomapp.controller.errors.exceptions.Genericas;

public class NotFoundException extends RuntimeException{
    
    public NotFoundException(String entidad){
        super(entidad.concat(" no se encuentra"));
    }
}
