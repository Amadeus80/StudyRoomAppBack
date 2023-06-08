package com.studyroom.studyroomapp.models.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservaPK implements Serializable{

    @ManyToOne
    @NotNull(message = "Debes indicar un asiento")
    private Asiento asiento;

    @ManyToOne
    @NotNull(message = "Debes indicar un horario")
    private Horario horario;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Debes indicar una fecha")
    private Date fecha;
}
