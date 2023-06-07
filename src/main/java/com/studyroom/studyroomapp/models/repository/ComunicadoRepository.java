package com.studyroom.studyroomapp.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studyroom.studyroomapp.models.entity.Comunicado;

public interface ComunicadoRepository extends JpaRepository<Comunicado, Long>{
    public List<Comunicado> findTop9ByOrderByIdAsc();
}
