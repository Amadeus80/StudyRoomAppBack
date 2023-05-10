package com.studyroom.studyroomapp.controller.errors.exceptions;

public class UsuarioEmailRepetidoException extends RuntimeException{
    
    public UsuarioEmailRepetidoException(String email){
        super("El email ".concat(email).concat(" ya se encuentra registrado"));
    }
}
