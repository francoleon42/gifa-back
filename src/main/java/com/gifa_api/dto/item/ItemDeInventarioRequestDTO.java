package com.gifa_api.dto.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDeInventarioRequestDTO {
    private String nombre;
    private Integer umbral;
    private Integer stock;
    private Integer cantCompraAutomatica;
}

