package com.studyroom.studyroomapp.controller;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.studyroom.studyroomapp.controller.errors.exceptions.ReservasExceptions.FormatoFechaException;
import com.studyroom.studyroomapp.models.entity.Horario;
import com.studyroom.studyroomapp.models.service.HorarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/horario")
@CrossOrigin("*")
public class HorarioController {

    @Autowired
    private HorarioService horarioService;

    @GetMapping("/lista")
    public List<Horario> findAll(HttpServletRequest request){
        return horarioService.findAll();
    }

    @GetMapping("/{id}")
    public Horario findById(@PathVariable(name = "id") Short id){
        Horario horario = horarioService.findById(id);
        if(horario == null){
            throw new NotFoundException("horario con id ".concat(String.valueOf(id)));
        }
        return horario;
    }

    @PostMapping("/add")
    public Horario save(@Valid @RequestBody Horario horario){
        return horarioService.save(horario);
    }

    @GetMapping("/horas-disponibles/{fecha}/{asientoId}")
    public List<Horario> listado(@PathVariable("fecha") String fecha, @PathVariable("asientoId") Short asientoId){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try{
            date = dateFormat.parse(fecha);
            return horarioService.listadoHorariosDisponiblesDiaYFecha(date, asientoId);
        }
        catch(ParseException e){
            throw new FormatoFechaException(fecha);
        }
    }
    
}
