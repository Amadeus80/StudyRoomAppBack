package com.studyroom.studyroomapp.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studyroom.studyroomapp.models.entity.Comunicado;
import com.studyroom.studyroomapp.models.repository.ComunicadoRepository;

@Service
public class ComunicadoServiceImp implements ComunicadoService{

    @Autowired
    private ComunicadoRepository comunicadoRepository;

    @Override
    public List<Comunicado> findAll() {
        return comunicadoRepository.findAll();
    }

    @Override
    public Comunicado save(Comunicado c) {
        return comunicadoRepository.save(c);
    }

    @Override
    public Comunicado findById(Long id) {
        return comunicadoRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        comunicadoRepository.deleteById(id);
    }
    
}
