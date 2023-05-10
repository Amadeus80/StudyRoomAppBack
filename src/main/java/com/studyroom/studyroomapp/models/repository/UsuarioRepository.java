package com.studyroom.studyroomapp.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studyroom.studyroomapp.models.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    public Usuario findByEmail(String email);
}
