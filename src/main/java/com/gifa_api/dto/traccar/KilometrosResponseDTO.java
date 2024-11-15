package com.gifa_api.dto.traccar;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class KilometrosResponseDTO {

    Integer deviceId;
    String deviceName;
    Integer maxSpeed;
    Integer averageSpeed;
    Integer distance;
    Integer spentFuel;
    Integer engineHours;
}
