package com.studyroom.studyroomapp.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.studyroom.studyroomapp.models.entity.Usuario;
import com.studyroom.studyroomapp.models.repository.UsuarioRepository;

@Service
public class UsuarioServiceImp implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public Usuario findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Override
    public Usuario save(Usuario u) {
        return usuarioRepository.save(u);
    }

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public void deletById(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public Page<Usuario> findAll(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    @Override
    public Page<Usuario> findByUsernameOrEmail(String username, String email, Pageable pageable) {
        return usuarioRepository.findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(username, email, pageable);
    }
}
