package com.studyroom.studyroomapp.models.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.studyroom.studyroomapp.controller.errors.exceptions.Genericas.DuplicadoException;
import com.studyroom.studyroomapp.models.entity.Reserva;
import com.studyroom.studyroomapp.models.entity.ReservaPK;
import com.studyroom.studyroomapp.models.repository.ReservaRepository;

@Service
public class ReservaServiceImp implements ReservaService{

    @Autowired
    private ReservaRepository reservaRepository;

    @Override
    public List<Reserva> findAll() {
        return reservaRepository.findAll();
    }

    @Override
    public Reserva findById(ReservaPK reservaPK) {
        return reservaRepository.findById(reservaPK).orElse(null);
    }

    @Override
    public Reserva save(Reserva r) {
        if(findById(r.getReservaPK()) != null){
            throw new DuplicadoException("Reserva");
        }
        return reservaRepository.save(r);
    }

    @Override
    public Page<Reserva> findByUsuario(Long id,Date fecha,Short hora, Pageable pageable) {
        return reservaRepository.findByUsuario(id, fecha,hora, pageable);
    }

    @Override
    public List<Reserva> findByAsientoAndFecha(Short idAsiento, Date fecha) {
        return reservaRepository.findByAsientoAndFecha(idAsiento, fecha);
    }

    @Override
    public void deleteById(ReservaPK reservaPK) {
        reservaRepository.deleteById(reservaPK);
    }
}
