package com.studyroom.studyroomapp.models.service;

import java.util.List;

import com.studyroom.studyroomapp.models.entity.Contacto;

public interface ContactoService {
    public List<Contacto> findAll();
    public Contacto findById(Long id);
    public List<Contacto> findByResuelta(boolean resuelta);
    public Contacto save(Contacto c);
}
