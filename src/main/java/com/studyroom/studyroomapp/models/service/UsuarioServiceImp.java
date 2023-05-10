package com.studyroom.studyroomapp.models.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studyroom.studyroomapp.models.entity.Usuario;
import com.studyroom.studyroomapp.models.repository.UsuarioRepository;

@Service
public class UsuarioServiceImp implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public Usuario save(Usuario u) {
        return save(u);
    }
    
}
