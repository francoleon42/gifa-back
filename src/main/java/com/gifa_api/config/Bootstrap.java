package com.gifa_api.config;

import com.gifa_api.model.Tarjeta;
import com.gifa_api.model.Vehiculo;
import com.gifa_api.repository.ITarjetaRepository;
import com.gifa_api.repository.IVehiculoRepository;
import com.gifa_api.utils.enums.EstadoDeHabilitacion;
import com.gifa_api.utils.enums.EstadoVehiculo;
import com.gifa_api.utils.enums.Rol;
import com.gifa_api.model.Usuario;
import com.gifa_api.repository.IUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class Bootstrap implements ApplicationRunner {
    private final IUsuarioRepository userRepository;
    private final IVehiculoRepository vehiculoRepository;
    private final ITarjetaRepository tarjetaRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Usuario admin = Usuario.builder()
                .usuario("admin")
                .contrasena("$2a$10$pnxUrqPKMU2HtJPt9RO1z.p5CK48s6Hus10QD1SFjCjvea9CxddKu")
                .rol(Rol.ADMINISTRADOR)
                .build();

        userRepository.save(admin);

        Usuario operador = Usuario.builder()
                .usuario("operador")
                .contrasena("$2a$10$pnxUrqPKMU2HtJPt9RO1z.p5CK48s6Hus10QD1SFjCjvea9CxddKu")
                .rol(Rol.OPERADOR)
                .build();

        userRepository.save(operador);

        Usuario supervisor = Usuario.builder()
                .usuario("supervisor")
                .contrasena("$2a$10$pnxUrqPKMU2HtJPt9RO1z.p5CK48s6Hus10QD1SFjCjvea9CxddKu")
                .rol(Rol.SUPERVISOR)
                .build();

        userRepository.save(supervisor);


        Vehiculo vehiculo1 = Vehiculo.builder()
                .patente("ABC123")
                .antiguedad(5)
                .kilometraje(100000)
                .litrosDeTanque(50)
                .modelo("Toyota Corolla")
                .estadoVehiculo(EstadoVehiculo.REPARADO)
                .estadoDeHabilitacion(EstadoDeHabilitacion.HABILITADO)
                .fechaVencimiento(LocalDate.of(2015, 12, 31))
                .build();


        vehiculoRepository.save(vehiculo1);


        Tarjeta tarjeta = Tarjeta.builder()
                .numero(123456789)
                .vehiculo(vehiculo1)
                .build();

        tarjetaRepository.save(tarjeta);


        vehiculo1.setTarjeta(tarjeta);
        vehiculoRepository.save(vehiculo1);


    }
}
