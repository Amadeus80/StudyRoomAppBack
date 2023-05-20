package com.studyroom.studyroomapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studyroom.studyroomapp.controller.errors.exceptions.Genericas.NotFoundException;
import com.studyroom.studyroomapp.models.entity.Asiento;
import com.studyroom.studyroomapp.models.service.AsientoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/asiento")
@CrossOrigin("*")
public class AsientoController {
    
    @Autowired
    private AsientoService asientoService;

    @GetMapping("/lista")
    public List<Asiento> findAll(){
        return asientoService.findAll();
    }

    @GetMapping("/{id}")
    public Asiento findById(@PathVariable(name = "id") Short id){
        Asiento asiento = asientoService.findById(id);
        if(asiento == null){
            throw new NotFoundException("asiento con id ".concat(String.valueOf(id)));
        }
        return asiento;
    }

    @PostMapping("/add")
    public Asiento save(@Valid @RequestBody Asiento asiento){
        return asientoService.save(asiento);
    }
}
