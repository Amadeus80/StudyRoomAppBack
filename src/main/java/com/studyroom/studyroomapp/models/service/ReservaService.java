package com.studyroom.studyroomapp.models.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.studyroom.studyroomapp.models.entity.Reserva;
import com.studyroom.studyroomapp.models.entity.ReservaPK;

public interface ReservaService {
    public List<Reserva> findAll();
    public Reserva findById(ReservaPK reservaPK);
    public Reserva save(Reserva r);
    public Page<Reserva> findByUsuario(Long id, Pageable pageable);
    public List<Reserva> findByAsientoAndFecha(Short idAsiento, Date fecha);
    public void deleteById(ReservaPK reservaPK);
}
