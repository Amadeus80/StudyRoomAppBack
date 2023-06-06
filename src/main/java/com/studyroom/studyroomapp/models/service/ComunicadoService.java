package com.studyroom.studyroomapp.models.service;

import java.util.List;

import com.studyroom.studyroomapp.models.entity.Comunicado;

public interface ComunicadoService {
    public List<Comunicado> findAll();
    public Comunicado save(Comunicado c);
    public Comunicado findById(Long id);
    public void deleteById(Long id);
}
