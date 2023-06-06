package com.studyroom.studyroomapp.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studyroom.studyroomapp.models.entity.Comunicado;

public interface ComunicadoRepository extends JpaRepository<Comunicado, Long>{
    
}
