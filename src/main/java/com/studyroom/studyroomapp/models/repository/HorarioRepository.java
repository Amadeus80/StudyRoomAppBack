package com.studyroom.studyroomapp.models.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.studyroom.studyroomapp.models.entity.Horario;

public interface HorarioRepository extends JpaRepository<Horario, Short>{
    public Optional<Horario> findByHora(String hora);

    @Query(
        "SELECT h from Horario h LEFT JOIN Reserva r on h.id = r.reservaPK.horario.id and r.reservaPK.fecha = ?1 and r.reservaPK.asiento.id = ?2 where r.reservaPK.fecha IS NULL order by h.id")
    public List<Horario> listadoHorariosDisponiblesDiaYFecha(Date fecha, Short asientoId);

    @Query(
        "SELECT h from Horario h LEFT JOIN Reserva r on h.id = r.reservaPK.horario.id and r.reservaPK.fecha = ?1 and r.reservaPK.asiento.id = ?2 where r.reservaPK.fecha IS NULL and h.id >= ?3 order by h.id")
    public List<Horario> listadoHorariosDisponiblesDiaYFechaDiaActual(Date fecha, Short asientoId, Short horarioId);
}
