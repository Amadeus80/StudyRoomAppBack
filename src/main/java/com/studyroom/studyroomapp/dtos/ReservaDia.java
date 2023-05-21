package com.studyroom.studyroomapp.dtos;

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
}
