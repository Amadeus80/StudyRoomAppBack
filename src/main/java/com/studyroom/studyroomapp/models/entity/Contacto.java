package com.studyroom.studyroomapp.models.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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
