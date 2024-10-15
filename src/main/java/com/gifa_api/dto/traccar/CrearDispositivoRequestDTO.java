package com.gifa_api.dto.traccar;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CrearDispositivoRequestDTO {
    private String name;
    private String uniqueId;


    // ver si agregar Mas datos
}