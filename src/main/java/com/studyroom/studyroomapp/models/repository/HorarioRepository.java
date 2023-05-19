package com.studyroom.studyroomapp.models.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studyroom.studyroomapp.models.entity.Horario;

public interface HorarioRepository extends JpaRepository<Horario, Short>{
    public Optional<Horario> findByHora(String hora);
}
