package com.studyroom.studyroomapp.models.service;

import java.util.List;

import com.studyroom.studyroomapp.models.entity.Horario;

public interface HorarioService {
    public List<Horario> findAll();
    public Horario findById(Short id);
    public Horario save(Horario h);
    public Horario findByHora(String hora);
}
