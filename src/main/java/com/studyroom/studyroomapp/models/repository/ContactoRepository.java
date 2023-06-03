package com.studyroom.studyroomapp.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studyroom.studyroomapp.models.entity.Contacto;

public interface ContactoRepository extends JpaRepository<Contacto, Long>{
    
}