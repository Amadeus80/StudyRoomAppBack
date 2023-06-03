package com.studyroom.studyroomapp.models.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.studyroom.studyroomapp.models.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    public Usuario findByEmail(String email);
    public Usuario findByUsername(String username);
    public Page<Usuario> findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(String username, String email, Pageable pageable);
}
