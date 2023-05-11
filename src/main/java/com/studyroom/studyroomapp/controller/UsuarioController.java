package com.studyroom.studyroomapp.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studyroom.studyroomapp.controller.errors.exceptions.UsuarioEmailRepetidoException;
import com.studyroom.studyroomapp.controller.errors.exceptions.UsuarioNombreRepetidoException;
import com.studyroom.studyroomapp.enums.Roles;
import com.studyroom.studyroomapp.models.entity.Usuario;
import com.studyroom.studyroomapp.models.service.RolService;
import com.studyroom.studyroomapp.models.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired 
    private RolService rolService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @GetMapping("/lista")
    public List<Usuario> findAll(){
        return usuarioService.findAll();
    }

    @PostMapping("/add")
    public Usuario save(@Valid @RequestBody Usuario usuario){
        if(usuarioService.findByEmail(usuario.getEmail()) != null){
            throw new UsuarioEmailRepetidoException(usuario.getEmail());
        }

        if(usuarioService.findByUsername(usuario.getUsername()) != null){
            throw new UsuarioNombreRepetidoException(usuario.getUsername());
        }

        usuario.setRoles(Arrays.asList(rolService.findByRol(Roles.ROLE_USER.name())));

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioService.save(usuario);
        return usuario;
    }

}
