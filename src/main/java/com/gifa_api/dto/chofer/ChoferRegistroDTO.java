package com.gifa_api.dto.chofer;

import com.gifa_api.model.Vehiculo;
import com.gifa_api.utils.enums.EstadoChofer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChoferRegistroDTO {
    String username;
    String password;
    String nombre;
}
