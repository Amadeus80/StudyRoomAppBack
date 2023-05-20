package com.studyroom.studyroomapp.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studyroom.studyroomapp.controller.errors.exceptions.Genericas.DuplicadoException;
import com.studyroom.studyroomapp.models.entity.Asiento;
import com.studyroom.studyroomapp.models.repository.AsientoRepository;

@Service
public class AsientoServiceImp implements AsientoService {

    @Autowired
    private AsientoRepository asientoRepository;

    @Override
    public List<Asiento> findAll() {
        return asientoRepository.findAll();
    }

    @Override
    public Asiento findById(Short id) {
        return asientoRepository.findById(id).orElse(null);
    }

    @Override
    public Asiento save(Asiento a) {
        if(findByAsiento(a.getAsiento()) != null){
            throw new DuplicadoException(a.getAsiento());
        }
        return asientoRepository.save(a);
    }

    @Override
    public Asiento findByAsiento(String asiento) {
        return asientoRepository.findByAsiento(asiento).orElse(null);
    }
    
}
