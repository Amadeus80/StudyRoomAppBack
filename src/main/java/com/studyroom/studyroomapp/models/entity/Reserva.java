package com.studyroom.studyroomapp.models.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reservas")
public class Reserva {
    
    @EmbeddedId
    private ReservaPK reservaPK;

    @ManyToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Usuario usuario;
}
