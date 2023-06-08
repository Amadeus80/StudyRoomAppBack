package com.studyroom.studyroomapp.dtos;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResolverConsulta {

    @NotBlank(message = "Indica un mensaje")
    private String mensaje;
}
