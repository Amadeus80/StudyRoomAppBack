package com.studyroom.studyroomapp.controller;

import java.util.Arrays;
import java.util.Date;
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
import com.studyroom.studyroomapp.dtos.ResolverConsulta;
import com.studyroom.studyroomapp.models.entity.Contacto;
import com.studyroom.studyroomapp.models.service.ContactoService;
import com.studyroom.studyroomapp.utils.correo.Correo;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/contacto")
@CrossOrigin("*")
public class ContactoController {
    
    @Autowired
    private ContactoService contactoService;

    @Autowired
    private Correo correo;

    @GetMapping("/lista")
    public List<Contacto> findAll(){
        return contactoService.findAll();
    }

    @GetMapping("/{id}")
    public Contacto findById(@PathVariable(name = "id") Long id){
        Contacto contacto = contactoService.findById(id);
        if(contacto == null){
            throw new NotFoundException("Contacto con id ".concat(String.valueOf(id)));
        }
        return contacto;
    }

    @PostMapping("/add")
    public Contacto save(@Valid @RequestBody Contacto contacto){
        contacto.setResuelta(false);
        contacto.setFecha(new Date());
        return contactoService.save(contacto);
    }

    @PostMapping("/resolver/{idConsulta}")
    private ResolverConsulta resolver(@Valid @RequestBody ResolverConsulta resolverConsulta, @PathVariable("idConsulta") Long id){
        Contacto contacto = contactoService.findById(id);
        if(contacto == null){
            throw new NotFoundException("Consulta con id ".concat(String.valueOf(id)));
        }
        correo.sendEmail(Arrays.asList(contactoService.findById(id).getEmail()), "Respuesta ".concat(contactoService.findById(id).getMensaje()), resolverConsulta.getMensaje());
        contacto.setResuelta(true);
        contactoService.save(contacto);
        return resolverConsulta;
    }

    @GetMapping("/lista-no-resueltas")
    public List<Contacto> listaNoResueltas(){
        return contactoService.findByResuelta(false);
    }
}
