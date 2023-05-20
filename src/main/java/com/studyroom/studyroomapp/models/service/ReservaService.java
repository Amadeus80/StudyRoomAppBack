package com.studyroom.studyroomapp.models.service;

import java.util.List;

import com.studyroom.studyroomapp.models.entity.Reserva;
import com.studyroom.studyroomapp.models.entity.ReservaPK;

public interface ReservaService {
    public List<Reserva> findAll();
    public Reserva findById(ReservaPK reservaPK);
    public Reserva save(Reserva r);
    public List<Reserva> findByUsuario(Long id);
}
