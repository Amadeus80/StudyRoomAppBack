package com.studyroom.studyroomapp.models.entity;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Debes indicar una fecha")
    private Date fecha;
}
