package com.studyroom.studyroomapp.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studyroom.studyroomapp.models.entity.Rol;
import com.studyroom.studyroomapp.models.repository.RolRepository;

@Service
public class RolServiceImp implements RolService{

    @Autowired
    private RolRepository rolRepository;

    @Override
    public List<Rol> findAll() {
        return rolRepository.findAll();
    }
    
    @Override
    public Rol findById(Long id) {
        return rolRepository.findById(id).orElse(null);
    }

    @Override
    public Rol save(Rol r) {
        return rolRepository.save(r);
    }

    @Override
    public Rol findByRol(String rol) {
        return rolRepository.findByRol(rol).orElse(null);
    }

    
}
