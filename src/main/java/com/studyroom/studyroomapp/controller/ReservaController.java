package com.studyroom.studyroomapp.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.studyroom.studyroomapp.controller.errors.exceptions.ReservasExceptions.FormatoFechaException;
import com.studyroom.studyroomapp.dtos.ReservaDia;
import com.studyroom.studyroomapp.models.entity.Asiento;
import com.studyroom.studyroomapp.models.entity.Reserva;
import com.studyroom.studyroomapp.models.entity.ReservaPK;
import com.studyroom.studyroomapp.models.service.AsientoService;
import com.studyroom.studyroomapp.models.service.HorarioService;
import com.studyroom.studyroomapp.models.service.ReservaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reserva")
@CrossOrigin("*")
public class ReservaController {
    
    @Autowired
    private ReservaService reservaService;

    @Autowired
    private AsientoService asientoService;

    @Autowired
    private HorarioService horarioService;

    
    @GetMapping("/lista")
    public List<Reserva> findAll(){
        return reservaService.findAll();
    }

    @GetMapping("/{fecha}")
    public List<ReservaDia> findAll(@PathVariable(name = "fecha") String fecha){

        List<Asiento> asientos = asientoService.findAll();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        List<ReservaDia> reservas = new ArrayList<>();
        try {
            date = dateFormat.parse(fecha);
            for (Asiento asiento : asientos) {
                if(reservaService.findByAsientoAndFecha(asiento.getId(), date).size() < horarioService.count()){
                    reservas.add(ReservaDia.builder().asiento(asiento).disponible(true).fecha(date).build()); 
                }
                else{
                    reservas.add(ReservaDia.builder().asiento(asiento).disponible(false).fecha(date).build()); 
                }
            }
        } catch (java.text.ParseException e) {
            throw new FormatoFechaException(fecha);
        };
        return reservas;
    }

    @GetMapping("/find")
    public Reserva findById(@Valid @RequestBody ReservaPK reservaPK){
        ReservaPK pk = ReservaPK.builder().asiento(asientoService.findById(reservaPK.getAsiento().getId())).horario(horarioService.findById(reservaPK.getHorario().getId())).fecha(reservaPK.getFecha()).build();
        Reserva reserva = reservaService.findById(pk);
        if(reserva == null){
            throw new NotFoundException("Reserva - ".concat(String.valueOf(pk)));
        }
        return reserva;
    }

    @GetMapping("/usuario/{id}")
    public List<Reserva> findById(@PathVariable(name = "id") Long id){
        return reservaService.findByUsuario(id);
    }

    @PostMapping("/add")
    public Reserva save(@Valid @RequestBody Reserva reserva){
        return reservaService.save(reserva);
    }
}
