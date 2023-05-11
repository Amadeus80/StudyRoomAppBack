package com.studyroom.studyroomapp.controller.errors.exceptions;

public class UsuarioNombreRepetidoException extends RuntimeException{
    
    public UsuarioNombreRepetidoException(String username){
        super("El usuario con username ".concat(username).concat(" ya se encuentra en el sistema"));
    }
}
