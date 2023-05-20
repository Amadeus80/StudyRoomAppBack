package com.studyroom.studyroomapp.models.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
