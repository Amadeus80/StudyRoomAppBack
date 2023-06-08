package com.studyroom.studyroomapp.controller;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.studyroom.studyroomapp.auth.service.JWTService;
import com.studyroom.studyroomapp.auth.service.JWTServiceImpl;
import com.studyroom.studyroomapp.controller.errors.exceptions.Genericas.NotFoundException;
import com.studyroom.studyroomapp.controller.errors.exceptions.UsuarioExceptions.UsuarioEmailRepetidoException;
import com.studyroom.studyroomapp.controller.errors.exceptions.UsuarioExceptions.UsuarioNombreRepetidoException;
import com.studyroom.studyroomapp.enums.Roles;
import com.studyroom.studyroomapp.models.entity.Rol;
import com.studyroom.studyroomapp.models.entity.Usuario;
import com.studyroom.studyroomapp.models.service.RolService;
import com.studyroom.studyroomapp.models.service.UsuarioService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

    @Autowired
    private JWTService jwtService;
    
    /* @GetMapping("/lista")
    public List<Usuario> findAll(){
        return usuarioService.findAll();
    } */

    @GetMapping("/lista")
    public Page<Usuario> findAll(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(name = "q",required = false) String query){
        Pageable pageRequest = PageRequest.of(page, size);
        Page<Usuario> usuarios;
        if(query == null || query.length() <= 0){
            usuarios = usuarioService.findAll(pageRequest);
        }
        else{
            usuarios = usuarioService.findByUsernameOrEmail(query, query, pageRequest);
        }
        return usuarios;
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

    @GetMapping("/find-user-logeado")
    public Usuario findUser(HttpServletRequest request){
        String token = request.getHeader(JWTServiceImpl.HEADER_STRING);
        Usuario usuario = usuarioService.findByEmail(jwtService.getUsername(token));
        if(usuario == null){
            throw new NotFoundException("El email "+String.valueOf(jwtService.getUsername(token)));
        }
        usuario.setPassword(null);
        return usuario;
        
    }

    @GetMapping("/userByUsername/{username}")
    public Usuario userByUsername(@PathVariable("username") String username){
        Usuario u = usuarioService.findByUsername(username);
        if(u == null){
            throw new NotFoundException("El username "+String.valueOf(username));
        }
        return u;
    }

    @GetMapping("/userByEmail/{email}")
    public Usuario userByEmail(@PathVariable("email") String email){
        Usuario u = usuarioService.findByEmail(email);
        if(u == null){
            throw new NotFoundException("El email "+String.valueOf(email));
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

    @PutMapping("/edit/{id}")
    public Usuario editAdmin(@Valid @RequestBody Usuario usuario, @PathVariable("id") Long id){
        Usuario u = usuarioService.findById(id);
        if(u == null){
            throw new NotFoundException("EL usuario con el id ".concat(String.valueOf(id)));
        }

        if(usuarioService.findByEmail(usuario.getEmail()) != null && !usuarioService.findById(id).getEmail().toUpperCase().equals(usuario.getEmail().toUpperCase())){
            throw new UsuarioEmailRepetidoException(usuario.getEmail());
        }

        if(usuarioService.findByUsername(usuario.getUsername()) != null && !usuarioService.findById(id).getUsername().toUpperCase().equals(usuario.getUsername().toUpperCase())){
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

        usuario.setPassword(u.getPassword());
        usuario.setId(id);
        usuarioService.save(usuario);
        return usuario;
    }

    @PutMapping("/edit-password/{id}")
    public Usuario editAdminPassword(@Valid @RequestBody Usuario usuario, @PathVariable("id") Long id){
        Usuario u = usuarioService.findById(id);
        if(u == null){
            throw new NotFoundException("EL usuario con el id ".concat(String.valueOf(id)));
        }

        if(usuarioService.findByEmail(usuario.getEmail()) != null && !usuarioService.findById(id).getEmail().toUpperCase().equals(usuario.getEmail().toUpperCase())){
            throw new UsuarioEmailRepetidoException(usuario.getEmail());
        }

        if(usuarioService.findByUsername(usuario.getUsername()) != null && !usuarioService.findById(id).getUsername().toUpperCase().equals(usuario.getUsername().toUpperCase())){
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
        usuario.setId(id);
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

    @PutMapping("/editUsernameUsuario")
    public Usuario editUsernameUsuario(@Valid @RequestBody Usuario usuario, HttpServletRequest request){
        String token = request.getHeader(JWTServiceImpl.HEADER_STRING);
        Usuario u = usuarioService.findByEmail(jwtService.getUsername(token));
        if(u == null){
            throw new NotFoundException("El username "+String.valueOf(jwtService.getUsername(token)));
        }

        if(usuarioService.findByUsername(usuario.getUsername()) != null){
            throw new UsuarioNombreRepetidoException(usuario.getUsername());
        }

        u.setUsername(usuario.getUsername());
        usuarioService.save(u);
        return u;
    }

    @PutMapping("/editPasswordUsuario")
    public Usuario editPasswordUsuario(@Valid @RequestBody Usuario usuario, HttpServletRequest request){
        String token = request.getHeader(JWTServiceImpl.HEADER_STRING);
        Usuario u = usuarioService.findByEmail(jwtService.getUsername(token));
        if(u == null){
            throw new NotFoundException("El username "+String.valueOf(jwtService.getUsername(token)));
        }

        u.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioService.save(u);
        return u;
    }

}
