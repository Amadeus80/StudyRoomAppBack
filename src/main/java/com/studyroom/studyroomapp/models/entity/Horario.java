package com.studyroom.studyroomapp.models.entity;

import com.studyroom.studyroomapp.utils.regexp.Regexp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "horarios")
public class Horario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Column(unique = true)
    @NotBlank(message = "Debes indicar una hora")
    @Pattern(regexp = Regexp.REGEX_HORA, message = "La hora debe coincidir con el formato hh:mm")
    private String hora; 


}
