package com.studyroom.studyroomapp.models.services;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.studyroom.studyroomapp.models.entity.Asiento;
import com.studyroom.studyroomapp.models.entity.Horario;
import com.studyroom.studyroomapp.models.entity.Reserva;
import com.studyroom.studyroomapp.models.entity.ReservaPK;
import com.studyroom.studyroomapp.models.entity.Usuario;
import com.studyroom.studyroomapp.models.repository.ReservaRepository;
import com.studyroom.studyroomapp.models.service.ReservaServiceImp;

public class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @InjectMocks
    private ReservaServiceImp reservaServiceImp;

    @BeforeEach
    public void BeforeEach(){
        MockitoAnnotations.openMocks(this);
        
    }
    
    @DisplayName("Test del listado de reservas")
    @Test
    public void findAll(){
        Reserva reserva = Reserva.builder().build();
        Reserva reserva2 = Reserva.builder().build();
        Reserva reserva3 = Reserva.builder().build();
        BDDMockito.given(reservaRepository.findAll()).willReturn(Arrays.asList(reserva, reserva2, reserva3));
        assertEquals(3,reservaServiceImp.findAll().size());
    }

    @DisplayName("Test del listado de reservas por id")
    @Test
    public void findById(){
        Optional<Reserva> reserva = Optional.of(Reserva.builder().build());
        BDDMockito.given(reservaRepository.findById(any(ReservaPK.class))).willReturn(reserva);
        assertNotNull(reservaServiceImp.findById(ReservaPK.builder().asiento(Asiento.builder().id((short)1).build()).horario(Horario.builder().id((short)1).build()).fecha(new Date()).build()));
    }

    @DisplayName("Test para guardar una reserva")
    @Test
    public void save(){
        Reserva reserva = Reserva
            .builder()
            .reservaPK(
                ReservaPK.builder().asiento(Asiento.builder().id((short)1).build()).horario(Horario.builder().id((short)1).build()).fecha(new Date()).build()
                )
                .usuario(Usuario.builder().id(1L).build())
                .build();
        BDDMockito.given(reservaRepository.save(any(Reserva.class))).willReturn(reserva);
        assertEquals(reserva.getReservaPK(), reservaServiceImp.save(reserva).getReservaPK());
        assertNotNull(reservaServiceImp.save(Reserva.builder().build()));
    }

    @DisplayName("Test para listar reservas de un usuario")
    @Test
    public void findByUsuario(){
        Reserva reserva = Reserva.builder().build();
        Reserva reserva2 = Reserva.builder().build();
        Reserva reserva3 = Reserva.builder().build();
        List<Reserva> listReservas = Arrays.asList(reserva, reserva2, reserva3);
        Page<Reserva> reservas = new PageImpl<>(listReservas);
        BDDMockito.given(reservaRepository.findByUsuario(any(Long.class), any(Date.class), any(Short.class), any(Pageable.class))).willReturn(reservas);
        assertEquals(3, reservaServiceImp.findByUsuario(1L, new Date(), (short)1, Pageable.ofSize(3)).getContent().size());
    }
}
