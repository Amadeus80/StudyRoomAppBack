package com.studyroom.studyroomapp.models.service;

import com.studyroom.studyroomapp.models.entity.Usuario;

public interface UsuarioService {
    public Usuario findByEmail(String email);
    public Usuario save(Usuario u);
}
