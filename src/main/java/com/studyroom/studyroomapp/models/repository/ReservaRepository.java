package com.studyroom.studyroomapp.models.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.studyroom.studyroomapp.models.entity.Reserva;
import com.studyroom.studyroomapp.models.entity.ReservaPK;

public interface ReservaRepository extends JpaRepository<Reserva, ReservaPK>{

    @Query("SELECT r FROM Reserva r WHERE r.usuario.id = ?1")
    public List<Reserva> findByUsuario(Long idUsuario);

    @Query("SELECT r FROM Reserva r WHERE r.reservaPK.asiento.id = ?1 and r.reservaPK.fecha = ?2")
    public List<Reserva> findByAsientoAndFecha(Short asiento, Date fecha);
}
