package com.gifa_api.service;

import java.time.OffsetDateTime;

public interface IKilometrajeVehiculoService {
    void addKilometrajeVehiculo(Integer kilometraje, OffsetDateTime fcha , Integer idVehiculo);
}
