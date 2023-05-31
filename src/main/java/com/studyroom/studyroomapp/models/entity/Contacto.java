package com.studyroom.studyroomapp.models.entity;


import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contactos")
public class Contacto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_usuario", nullable = false)
    @NotBlank(message = "Debes indicar un nombre de usuario")
    private String nombreUsuario;

    @Column(nullable = false)
    @Email(message = "Tienes que introducir un formato de email válido")
    @NotBlank(message = "Debes indicar un email")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "Debes indicar un telefono")
    private String telefono;

    @Column(nullable = false)
    @NotBlank(message = "Debes indicar un mensaje")
    private String mensaje;

    @Column(nullable = false)
    private boolean resuelta;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
}
