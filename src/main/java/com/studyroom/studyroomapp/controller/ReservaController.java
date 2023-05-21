package com.studyroom.studyroomapp.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studyroom.studyroomapp.auth.service.JWTService;
import com.studyroom.studyroomapp.auth.service.JWTServiceImpl;
import com.studyroom.studyroomapp.controller.errors.exceptions.Genericas.NotFoundException;
import com.studyroom.studyroomapp.controller.errors.exceptions.ReservasExceptions.FormatoFechaException;
import com.studyroom.studyroomapp.dtos.ReservaDia;
import com.studyroom.studyroomapp.models.entity.Asiento;
import com.studyroom.studyroomapp.models.entity.Reserva;
import com.studyroom.studyroomapp.models.entity.ReservaPK;
import com.studyroom.studyroomapp.models.entity.Usuario;
import com.studyroom.studyroomapp.models.service.AsientoService;
import com.studyroom.studyroomapp.models.service.HorarioService;
import com.studyroom.studyroomapp.models.service.ReservaService;
import com.studyroom.studyroomapp.models.service.UsuarioService;
import com.studyroom.studyroomapp.utils.correo.Correo;

import jakarta.servlet.http.HttpServletRequest;
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

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private Correo correo;

    @Autowired
    private JWTService jwtService;

    
    @GetMapping("/lista")
    public List<Reserva> findAll(){
        return reservaService.findAll();
    }

    @GetMapping("/{fecha}")
    public List<ReservaDia> findByFecha(@PathVariable(name = "fecha") String fecha){

        List<Asiento> asientos = asientoService.findAll();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        List<ReservaDia> reservas = new ArrayList<>();
        try {
            date = dateFormat.parse(fecha);
            for (Asiento asiento : asientos) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                if(reservaService.findByAsientoAndFecha(asiento.getId(), date).size() < horarioService.count()){
                    reservas.add(ReservaDia.builder().asiento(asiento).disponible(true).fecha(calendar.getTime()).build()); 
                }
                else{
                    reservas.add(ReservaDia.builder().asiento(asiento).disponible(false).fecha(calendar.getTime()).build()); 
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
    public Reserva save(@Valid @RequestBody Reserva reserva, HttpServletRequest request){
        String token =  request.getHeader(JWTServiceImpl.HEADER_STRING);
        String usuarioEmail = jwtService.getUsername(token);
        Usuario usuario = usuarioService.findByEmail(usuarioEmail);
        reserva.setUsuario(usuario);
        Reserva r = reservaService.save(reserva);
        String asiento = asientoService.findById(r.getReservaPK().getAsiento().getId()).getAsiento();
        String horario = horarioService.findById(r.getReservaPK().getHorario().getId()).getHora();

        String subject = "Reserva relizada para el día ".concat(r.getReservaPK().getFecha().toString());
        String message = "Has realizado una reserva para el día "
            .concat(r.getReservaPK().getFecha().toString())
            .concat(" en el asiento ")
            .concat(asiento)
            .concat(" a las ")
            .concat(horario);
        correo.sendEmail(subject, message, r.getUsuario().getEmail());
        return r;
    }

    @DeleteMapping("/delete")
    public void deleteById(@Valid @RequestBody ReservaPK reservaPK){
        reservaService.deleteById(reservaPK);
    }

}
