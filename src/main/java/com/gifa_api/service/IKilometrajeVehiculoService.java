package com.gifa_api.service;

import java.time.OffsetDateTime;

public interface IKilometrajeVehiculoService {
    void addKilometrajeVehiculo(double kilometraje, OffsetDateTime fcha , Integer idVehiculo);
}
