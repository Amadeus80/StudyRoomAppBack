package com.studyroom.studyroomapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studyroom.studyroomapp.controller.errors.exceptions.UsuarioEmailRepetidoException;
import com.studyroom.studyroomapp.models.entity.Usuario;
import com.studyroom.studyroomapp.models.service.UsuarioService;

@RestController
@RequestMapping("/api/user")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping("/lista")
    public List<Usuario> findAll(){
        return usuarioService.findAll();
    }

    @PostMapping("/add")
    public Usuario save(@RequestBody Usuario usuario){
        if(usuarioService.findByEmail(usuario.getEmail()) != null){
            throw new UsuarioEmailRepetidoException(usuario.getEmail());
        }
        usuarioService.save(usuario);
        return usuario;
    }

}
