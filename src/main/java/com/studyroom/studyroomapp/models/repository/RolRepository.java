package com.studyroom.studyroomapp.models.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studyroom.studyroomapp.models.entity.Rol;

public interface RolRepository extends JpaRepository<Rol, Long> {
    public Optional<Rol> findByRol(String rol);
}
