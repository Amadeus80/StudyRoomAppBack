package com.studyroom.studyroomapp.models.service;

import java.util.List;

import com.studyroom.studyroomapp.models.entity.Usuario;

public interface UsuarioService {
    public Usuario findByEmail(String email);
    public Usuario findByUsername(String username);
    public List<Usuario> findAll();
    public void deletById(Long id);
    public Usuario save(Usuario u);
}
