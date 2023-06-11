package com.studyroom.studyroomapp.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.studyroom.studyroomapp.auth.service.JWTService;
import com.studyroom.studyroomapp.auth.service.JWTServiceImpl;
import com.studyroom.studyroomapp.controller.errors.exceptions.Genericas.NotFoundException;
import com.studyroom.studyroomapp.controller.errors.exceptions.ReservasExceptions.BorradoReservaException;
import com.studyroom.studyroomapp.controller.errors.exceptions.ReservasExceptions.FechaAnteriorException;
import com.studyroom.studyroomapp.controller.errors.exceptions.ReservasExceptions.FormatoFechaException;
import com.studyroom.studyroomapp.dtos.ReservaDia;
import com.studyroom.studyroomapp.models.entity.Asiento;
import com.studyroom.studyroomapp.models.entity.Horario;
import com.studyroom.studyroomapp.models.entity.Reserva;
import com.studyroom.studyroomapp.models.entity.ReservaPK;
import com.studyroom.studyroomapp.models.entity.Usuario;
import com.studyroom.studyroomapp.models.service.AsientoService;
import com.studyroom.studyroomapp.models.service.HorarioService;
import com.studyroom.studyroomapp.models.service.ReservaService;
import com.studyroom.studyroomapp.models.service.UsuarioService;
import com.studyroom.studyroomapp.utils.correo.Correo;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
    private JWTService jwtService;

    @Autowired
    private Correo correo;

    
    @GetMapping("/lista")
    public List<Reserva> findAll(){
        return reservaService.findAll();
    }

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

    @PostMapping("/find")
    public Reserva findById(@Valid @RequestBody ReservaPK reservaPK){
        ReservaPK pk = ReservaPK.builder().asiento(asientoService.findById(reservaPK.getAsiento().getId())).horario(horarioService.findById(reservaPK.getHorario().getId())).fecha(reservaPK.getFecha()).build();
        Reserva reserva = reservaService.findById(pk);
        if(reserva == null){
            throw new NotFoundException("Reserva - ".concat(String.valueOf(pk)));
        }
        return reserva;
    }

    @GetMapping("/usuario")
    public Page<Reserva> findById(HttpServletRequest request, @RequestParam(name = "page", defaultValue = "0") int page){
        String token =  request.getHeader(JWTServiceImpl.HEADER_STRING);
        String usuarioEmail = jwtService.getUsername(token);
        Usuario usuario = usuarioService.findByEmail(usuarioEmail);
        if(usuario == null){
            throw new NotFoundException("Usuario ");
        }

        Date fecha = new Date();
        Horario horario = horarioService.findByHora(horaActual());
        Pageable pageRequest = PageRequest.of(page, 5);
        return reservaService.findByUsuario(usuario.getId(),fecha,horario.getId(), pageRequest);
    }

    @PostMapping("/add")
    public Reserva save(@Valid @RequestBody Reserva reserva, HttpServletRequest request){
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

        Date fechaReserva = fechaSinHoras(reserva.getReservaPK().getFecha());
        if(fechaReserva.before(fechaSinHoras(new Date()))){
            throw new FechaAnteriorException(fechaReserva.toString());
        }

        String token =  request.getHeader(JWTServiceImpl.HEADER_STRING);
        String usuarioEmail = jwtService.getUsername(token);
        Usuario usuario = usuarioService.findByEmail(usuarioEmail);
        reserva.setUsuario(usuario);
        Reserva r = reservaService.save(reserva);
        String asiento = asientoService.findById(r.getReservaPK().getAsiento().getId()).getAsiento();
        String horario = horarioService.findById(r.getReservaPK().getHorario().getId()).getHora();

        Date fecha = r.getReservaPK().getFecha();
        String subject = "Reserva relizada para el día ".concat(dateFormat.format(fecha));
        String message = "Has realizado una reserva para el día "
            .concat(dateFormat.format(fecha))
            .concat(" en el asiento ")
            .concat(asiento)
            .concat(" a las ")
            .concat(horario);
        correo.sendEmail(Arrays.asList(r.getUsuario().getEmail()) ,subject, message);
        return r;
    }

    @DeleteMapping("/delete")
    public void deleteById(@Valid @RequestBody ReservaPK reservaPK, HttpServletRequest request){
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        String token =  request.getHeader(JWTServiceImpl.HEADER_STRING);
        String usuarioEmail = jwtService.getUsername(token);
        Usuario usuario = usuarioService.findByEmail(usuarioEmail);
        if(usuario == null){
            throw new NotFoundException("Usuario ");
        }
        Reserva reserva = reservaService.findById(reservaPK);
        if(reserva == null){
            throw new NotFoundException("Reserva ");
        }
        if(reserva.getUsuario().getId() != usuario.getId()){
            throw new BorradoReservaException(reservaPK.toString());
        }

        Date fecha = reservaPK.getFecha();
        Asiento asiento = asientoService.findById(reservaPK.getAsiento().getId());
        String asientoStr = "";
        if(asiento != null){
            asientoStr = asiento.getAsiento();
        }
        Horario horario = horarioService.findById(reservaPK.getHorario().getId());
        String horarioStr = "";
        if(horario != null){
            horarioStr = horario.getHora();
        }
        String subject = "Reserva cancelada para el día ".concat(dateFormat.format(fecha));
        String message = "Se ha cancelado la reserva para el día "
            .concat(dateFormat.format(fecha))
            .concat(" en el asiento ")
            .concat(asientoStr)
            .concat(" a las ")
            .concat(horarioStr);
        correo.sendEmail(Arrays.asList(usuario.getEmail()) ,subject, message);
        reservaService.deleteById(reservaPK);
    }

}
