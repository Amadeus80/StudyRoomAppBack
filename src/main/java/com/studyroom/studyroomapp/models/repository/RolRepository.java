package com.studyroom.studyroomapp.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studyroom.studyroomapp.models.entity.Rol;

public interface RolRepository extends JpaRepository<Rol, Long> {
    public Rol findByRol(String rol);
}
