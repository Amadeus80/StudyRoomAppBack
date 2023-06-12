package com.studyroom.studyroomapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studyroom.studyroomapp.models.entity.Comunicado;
import com.studyroom.studyroomapp.models.service.ComunicadoService;

@RestController
@RequestMapping("/api/comunicados")
public class ComunicadoController {

    @Autowired
    private ComunicadoService comunicadoService;
    
    /* Devuelve la lista de comunicados */
    @GetMapping("/lista")
    public List<Comunicado> lista(){
        return comunicadoService.findAll();
    }
}
