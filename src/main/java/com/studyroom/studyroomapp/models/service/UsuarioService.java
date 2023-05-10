package com.studyroom.studyroomapp.models.service;

import java.util.List;

import com.studyroom.studyroomapp.models.entity.Usuario;

public interface UsuarioService {
    public Usuario findByEmail(String email);
    public List<Usuario> findAll();
    public void deletById(Long id);
    public Usuario save(Usuario u);
}
