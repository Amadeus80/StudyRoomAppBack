package com.studyroom.studyroomapp.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.studyroom.studyroomapp.models.entity.Usuario;

public interface UsuarioService {
    public Usuario findById(Long id);
    public Usuario findByEmail(String email);
    public Usuario findByUsername(String username);
    public List<Usuario> findAll();
    public Page<Usuario> findAll(Pageable pageable);
    public Page<Usuario> findByUsernameOrEmail(String username,String email,Pageable pageable);
    public void deletById(Long id);
    public Usuario save(Usuario u);
}
