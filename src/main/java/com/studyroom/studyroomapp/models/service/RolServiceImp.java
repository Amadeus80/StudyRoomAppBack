package com.studyroom.studyroomapp.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.studyroom.studyroomapp.models.entity.Rol;
import com.studyroom.studyroomapp.models.repository.RolRepository;

public class RolServiceImp implements RolService{

    @Autowired
    private RolRepository rolRepository;

    @Override
    public List<Rol> findALl() {
        return rolRepository.findAll();
    }

    @Override
    public Rol save(Rol r) {
        return rolRepository.save(r);
    }
    
}
