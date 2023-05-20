package com.studyroom.studyroomapp.models.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studyroom.studyroomapp.models.entity.Asiento;

public interface AsientoRepository extends JpaRepository<Asiento, Short>{
    public Optional<Asiento> findByAsiento(String asiento);
}
