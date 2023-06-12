package com.studyroom.studyroomapp.controller;

import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/horario")
@CrossOrigin("*")
public class HorarioController {

    @Autowired
    private HorarioService horarioService;

    /* Funcion para quitar las horas de las fechas para poder comparar dos fechas sin que suponga un problema las horas */
    public static Date fechaSinHoras(Date date){
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /* Obtengo la hora actual de la zona horaria de españa y comprueba si ya han pasado más de y media para devolver una hora o otra y así hacer la consulta a la bd */
    private static String horaActual(){
        LocalDateTime ahora= LocalDateTime.now();
        ZoneId zonaHorariaEspaña = ZoneId.of("Europe/Madrid");
        ZonedDateTime zonedDateTime = ahora.atZone(ZoneOffset.systemDefault());
        ZonedDateTime horaEspaña = zonedDateTime.withZoneSameInstant(zonaHorariaEspaña);
        int hora = horaEspaña.getHour();
        int minutos = horaEspaña.getMinute();
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

    /* Listado de horas */
    @GetMapping("/lista")
    public List<Horario> findAll(HttpServletRequest request){
        return horarioService.findAll();
    }

    /* Le pasamos una hora y devolvemos la entidad relacionado en la base de datos */
    @GetMapping("/hora/{hora}")
    public Horario obtenerHorario(@PathVariable(name = "hora") String hora){
        Horario horario = horarioService.findByHora(hora);
        if(horario == null){
            throw new NotFoundException("La hora ".concat(hora));
        }
        return horario;
    }

    /* Buscamos una hora por su id */
    @GetMapping("/{id}")
    public Horario findById(@PathVariable(name = "id") Short id){
        Horario horario = horarioService.findById(id);
        if(horario == null){
            throw new NotFoundException("horario con id ".concat(String.valueOf(id)));
        }
        return horario;
    }

    /* Añade un hora */
    @PostMapping("/add")
    public Horario save(@Valid @RequestBody Horario horario){
        return horarioService.save(horario);
    }

    /* Te devuelve lkas horas disponibles en una fecha y un asiento concreto, tiene en cuenta si la fecha es del día de hoy para así solo mostrar las horas posteriores a la actual */
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
