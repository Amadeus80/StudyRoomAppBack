package com.studyroom.studyroomapp.userDetails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.studyroom.studyroomapp.models.entity.Usuario;
import com.studyroom.studyroomapp.models.service.UsuarioService;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private UsuarioService usuarioService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuarioLogeado = usuarioService.findByEmail(email);

        if(usuarioLogeado == null){
            logger.error("Error login: no existe el usuario");
            throw new UsernameNotFoundException("El usuario no existe en el sistema");
        }

        CustomUserDetail usuario = new CustomUserDetail(usuarioLogeado);
        
        if(usuario.getAuthorities().isEmpty()){
            logger.error("El usuario no tiene roles");
        }

        return usuario;
    }
    
}
