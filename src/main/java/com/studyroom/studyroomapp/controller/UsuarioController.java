package com.studyroom.studyroomapp.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.studyroom.studyroomapp.controller.errors.exceptions.Genericas.NotFoundException;
import com.studyroom.studyroomapp.controller.errors.exceptions.UsuarioExceptions.UsuarioEmailRepetidoException;
import com.studyroom.studyroomapp.controller.errors.exceptions.UsuarioExceptions.UsuarioNombreRepetidoException;
import com.studyroom.studyroomapp.enums.Roles;
import com.studyroom.studyroomapp.models.entity.Rol;
import com.studyroom.studyroomapp.models.entity.Usuario;
import com.studyroom.studyroomapp.models.service.RolService;
import com.studyroom.studyroomapp.models.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired 
    private RolService rolService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    /* @GetMapping("/lista")
    public List<Usuario> findAll(){
        return usuarioService.findAll();
    } */

    @GetMapping("/lista")
    public Page<Usuario> findAll(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size){
        Pageable pageRequest = PageRequest.of(page, size);
        return usuarioService.findAll(pageRequest);
    }

    @GetMapping("/lista-roles")
    public List<Rol> findAllRoles(){
        return rolService.findAll();
    }

    @GetMapping("/{id}")
    public Usuario findById(@PathVariable("id") Long id){
        Usuario u = usuarioService.findById(id);
        if(u == null){
            throw new NotFoundException("El id "+String.valueOf(id));
        }
        return u;
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

    @PostMapping("/add-admin")
    public Usuario saveAdmin(@Valid @RequestBody Usuario usuario){
        if(usuarioService.findByEmail(usuario.getEmail()) != null){
            throw new UsuarioEmailRepetidoException(usuario.getEmail());
        }

        if(usuarioService.findByUsername(usuario.getUsername()) != null){
            throw new UsuarioNombreRepetidoException(usuario.getUsername());
        }

        if(usuario.getRoles() == null){
            usuario.setRoles(Arrays.asList(rolService.findByRol(Roles.ROLE_USER.name())));
        }
        else{
            for (Rol rol : usuario.getRoles()) {
                if(rolService.findById(rol.getId()) == null){
                    throw new NotFoundException("El rol con id "+String.valueOf(rol.getId()));
                }
            }
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioService.save(usuario);
        return usuario;
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, String> delete(@PathVariable("id") Long id){
        Usuario u = usuarioService.findById(id);
        if(u == null){
            throw new NotFoundException("El id "+String.valueOf(id));
        }
        usuarioService.deletById(id);
        Map<String, String> mensaje = new HashMap<>();
        mensaje.put("mensaje", "Se ha borrado el usuario con id ".concat(String.valueOf(id)));
        return mensaje;
    }
}
