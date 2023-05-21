package com.studyroom.studyroomapp.dtos;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.studyroom.studyroomapp.models.entity.Asiento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservaDia {
    private Asiento asiento;
    private boolean disponible;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha;
}
