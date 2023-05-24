package com.studyroom.studyroomapp.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studyroom.studyroomapp.models.entity.Contacto;
import com.studyroom.studyroomapp.models.repository.ContactoRepository;

@Service
public class ContactoServiceImp implements ContactoService{

    @Autowired
    private ContactoRepository contactoRepository;

    @Override
    public List<Contacto> findAll() {
        return contactoRepository.findAll();
    }

    @Override
    public Contacto findById(Long id) {
        return contactoRepository.findById(id).orElse(null);
    }

    @Override
    public Contacto save(Contacto c) {
        return contactoRepository.save(c);
    }
    
}
