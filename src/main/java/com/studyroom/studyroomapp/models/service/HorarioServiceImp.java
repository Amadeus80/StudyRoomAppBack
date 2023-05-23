package com.studyroom.studyroomapp.models.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studyroom.studyroomapp.controller.errors.exceptions.Genericas.DuplicadoException;
import com.studyroom.studyroomapp.models.entity.Horario;
import com.studyroom.studyroomapp.models.repository.HorarioRepository;

@Service
public class HorarioServiceImp implements HorarioService{

    @Autowired
    private HorarioRepository horarioRepository;

    @Override
    public List<Horario> findAll() {
        return horarioRepository.findAll();
    }

    @Override
    public Horario findById(Short id) {
        return horarioRepository.findById(id).orElse(null);
    }

    @Override
    public Horario save(Horario h) {
        if(findByHora(h.getHora()) != null){
            throw new DuplicadoException(h.getHora());
        }
        return horarioRepository.save(h);
    }

    @Override
    public Horario findByHora(String hora) {
        return horarioRepository.findByHora(hora).orElse(null);
    }

    @Override
    public Long count() {
        return horarioRepository.count();
    }

    @Override
    public List<Horario> listadoHorariosDisponiblesDiaYFecha(Date fecha, Short asientoId) {
        return horarioRepository.listadoHorariosDisponiblesDiaYFecha(fecha, asientoId);
    }
    
}
