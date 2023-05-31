package com.studyroom.studyroomapp.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studyroom.studyroomapp.models.entity.Contacto;

public interface ContactoRepository extends JpaRepository<Contacto, Long>{
    public List<Contacto> findByResueltaOrderByFechaAsc(boolean resuelta);
}
