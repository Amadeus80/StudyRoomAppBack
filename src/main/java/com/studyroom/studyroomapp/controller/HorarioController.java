package com.studyroom.studyroomapp.controller;

import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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

    public static Date fechaSinHoras(Date date){
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private static String horaActual(){
        LocalDateTime ahora= LocalDateTime.now();
                int hora = ahora.getHour();
                int minutos = ahora.getMinute();
                String horaString = "";

                if(minutos > 30){
                    hora++;
                }

                if(hora < 10){
                    horaString = "0"+String.valueOf(hora);
                }
                else{
                    horaString = String.valueOf(hora);
                }
                
                
                return horaString + ":00";
    }

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
            if(date.equals(fechaSinHoras(new Date()))){
                Horario horario = horarioService.findByHora(horaActual());
                if(horario != null){
                    return horarioService.listadoHorariosDisponiblesDiaYFechaDiaActual(date, asientoId, horario.getId());
                }
            }
            return horarioService.listadoHorariosDisponiblesDiaYFecha(date, asientoId);
        }
        catch(ParseException e){
            throw new FormatoFechaException(fecha);
        }
    }
    
}
