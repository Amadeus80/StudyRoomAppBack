package com.studyroom.studyroomapp.models.service;

import java.util.List;

import com.studyroom.studyroomapp.models.entity.Asiento;

public interface AsientoService {
    public List<Asiento> findAll();
    public Asiento findById(Short id);
    public Asiento save(Asiento a);
    public Asiento findByAsiento(String asiento);
}
