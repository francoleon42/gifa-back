package com.gifa_api.config;

import com.gifa_api.model.*;
import com.gifa_api.repository.*;
import com.gifa_api.utils.enums.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Bootstrap implements ApplicationRunner {
    private final IUsuarioRepository userRepository;
    private final IVehiculoRepository vehiculoRepository;
    private final ITarjetaRepository tarjetaRepository;
    private final ICargaCombustibleRepository cargaCombustibleRepository;
    private final IGpsDataRepository gpsDataRepository;
    private final ItemDeInventarioRepository itemDeInventarioRepository;
    private final IPedidoRepository pedidoRepository;
    private final IMantenimientoRepository mantenimientoRepository;
    private final IProveedorRepository proveedorRepository;
    private final IProveedorDeItemRepository proveedorDeParteRepository;
    private final IGestorDePedidosRepository igestorDePedidosRepository;
    private final IKilometrajeVehiculoRepository kilometrajeVehiculoRepository;
    private final IItemUsadoMantenimientoRepository itemUsadoMantenimientoRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Crear usuarios con builder
        Usuario admin = Usuario.builder()
                .usuario("admin")
                .contrasena("$2a$10$RRAzywJFxaAG3pRlHXep6u6VNKi5KOTT3M8GCxDPHpAyZ0ofX2Bcu")
                .rol(Rol.ADMINISTRADOR)
                .build();

        Usuario operador = Usuario.builder()
                .usuario("operador")
                .contrasena("$2a$10$RRAzywJFxaAG3pRlHXep6u6VNKi5KOTT3M8GCxDPHpAyZ0ofX2Bcu")
                .rol(Rol.OPERADOR)
                .build();

        Usuario supervisor = Usuario.builder()
                .usuario("supervisor")
                .contrasena("$2a$10$RRAzywJFxaAG3pRlHXep6u6VNKi5KOTT3M8GCxDPHpAyZ0ofX2Bcu")
                .rol(Rol.SUPERVISOR)
                .build();

        Usuario gerente = Usuario.builder()
                .usuario("gerente")
                .contrasena("$2a$10$RRAzywJFxaAG3pRlHXep6u6VNKi5KOTT3M8GCxDPHpAyZ0ofX2Bcu")
                .rol(Rol.GERENTE)
                .build();

        userRepository.saveAll(List.of(admin, operador, supervisor, gerente));


        GestorDePedidos gestorDePedidos = GestorDePedidos.builder()
                .presupuesto(100.00)
                .cantDePedidoAutomatico(20)
                .gerente(gerente)
                .build();

        igestorDePedidosRepository.save(gestorDePedidos);

        // Crear tarjetas con builder
        Tarjeta tarjeta1 = Tarjeta.builder()
                .numero(12345)
                .build();

        Tarjeta tarjeta2 = Tarjeta.builder()
                .numero(54321)
                .build();

        tarjetaRepository.saveAll(List.of(tarjeta1, tarjeta2));

        // Crear vehículos con builder
        Vehiculo vehiculo1 = Vehiculo.builder()
                .patente("ABC123")
                .antiguedad(5)
                .kilometraje(10000)
                .litrosDeTanque(60)
                .modelo("Modelo X")
                .estadoVehiculo(EstadoVehiculo.REPARADO)
                .estadoDeHabilitacion(EstadoDeHabilitacion.HABILITADO)
                .fechaVencimiento(LocalDate.now().plusYears(1))
                .tarjeta(tarjeta1)
                .build();

        Vehiculo vehiculo2 = Vehiculo.builder()
                .patente("XYZ789")
                .antiguedad(3)
                .kilometraje(8000)
                .litrosDeTanque(50)
                .modelo("Modelo Y")
                .estadoVehiculo(EstadoVehiculo.EN_REPARACION)
                .estadoDeHabilitacion(EstadoDeHabilitacion.HABILITADO)
                .fechaVencimiento(LocalDate.now().plusYears(1))
                .tarjeta(tarjeta2)
                .build();

        vehiculoRepository.saveAll(List.of(vehiculo1, vehiculo2));

        // Crear cargas de combustible con builder
        for (int i = 1; i <= 10; i++) {
            CargaCombustible carga = CargaCombustible.builder()
                    .cantidadLitros(i * 10)
                    .fechaHora(LocalDateTime.now())
                    .precioPorLitro(100f + i)
                    .tarjeta(tarjeta1)
                    .build();
            cargaCombustibleRepository.save(carga);
        }

        // Crear datos de GPS con builder
        for (int i = 1; i <= 5; i++) {
            GpsData gpsData = GpsData.builder()
                    .latitud(40.0f + i)
                    .longitud(-74.0f - i)
                    .fechaHora(Instant.now())
                    .distanciaDesdeUltimaPosicion(5.0f * i)
                    .vehiculo(vehiculo1)
                    .build();
            gpsDataRepository.save(gpsData);
        }

        // Crear ítems de inventario con builder
        ItemDeInventario item1 = ItemDeInventario.builder()
                .nombre("Filtro de aceite")
                .umbral(5)
                .stock(10)
                .cantCompraAutomatica(3)
                .build();

        ItemDeInventario item2 = ItemDeInventario.builder()
                .nombre("Neumático")
                .umbral(3)
                .stock(8)
                .cantCompraAutomatica(2)
                .build();

        itemDeInventarioRepository.saveAll(List.of(item1, item2));


        // Crear proveedores con builder y relaciones con ítems
        Proveedor proveedor1 = Proveedor.builder()
                .nombre("Proveedor1")
                .email("proveedor1@email.com")
                .build();

        Proveedor proveedor2 = Proveedor.builder()
                .nombre("Proveedor2")
                .email("proveedor2@email.com")
                .build();

        proveedorRepository.saveAll(List.of(proveedor1, proveedor2));

        ProveedorDeItem proveedorDeItem1 = ProveedorDeItem.builder()

                .proveedor(proveedor1)
                .precio(100.00)
                .itemDeInventario(item2)
                .build();

        ProveedorDeItem proveedorDeItem2 = ProveedorDeItem.builder()
                .proveedor(proveedor2)
                .precio(100.00)
                .itemDeInventario(item1)
                .build();

        proveedorDeParteRepository.saveAll(List.of(proveedorDeItem1, proveedorDeItem2));

        // Crear pedidos con builder
        Pedido pedido1 = Pedido.builder()
                .fecha(LocalDate.now())
                .cantidad(5)
                .motivo("Reponer stock")
                .item(item1)
                .estadoPedido(EstadoPedido.PENDIENTE)
                .build();

        Pedido pedido2 = Pedido.builder()
                .fecha(LocalDate.now())
                .cantidad(3)
                .motivo("Reemplazo de neumático")
                .item(item2)
                .estadoPedido(EstadoPedido.PENDIENTE)
                .build();

        pedidoRepository.saveAll(List.of(pedido1, pedido2));

        // Crear mantenimientos con builder
        Mantenimiento mantenimiento1 = Mantenimiento.builder()
                .fechaInicio(LocalDate.now())
                .fechaFinalizacion(LocalDate.now().plusDays(1))
                .asunto("Cambio de aceite")
                .estadoMantenimiento(EstadoMantenimiento.PENDIENTE)
                .operador(operador)
                .vehiculo(vehiculo1)
                .build();

        Mantenimiento mantenimiento2 = Mantenimiento.builder()
                .fechaInicio(LocalDate.now())
                .fechaFinalizacion(LocalDate.now().plusDays(2))
                .asunto("Cambio de neumático")
                .estadoMantenimiento(EstadoMantenimiento.PENDIENTE)
                .operador(operador)
                .vehiculo(vehiculo2)
                .build();

        mantenimientoRepository.saveAll(List.of(mantenimiento1, mantenimiento2));

        //Crear item utilizado en mantenimiento
        ItemUsadoMantenimiento itemUsadoMantenimiento1 = ItemUsadoMantenimiento.builder()
                .mantenimiento(mantenimiento1)
                .itemDeInventario(item1)
                .cantidad(2)
                .build();

        ItemUsadoMantenimiento itemUsadoMantenimiento2 = ItemUsadoMantenimiento.builder()
                .mantenimiento(mantenimiento2)
                .itemDeInventario(item2)
                .cantidad(4)
                .build();

        itemUsadoMantenimientoRepository.saveAll(List.of(itemUsadoMantenimiento1, itemUsadoMantenimiento2));



        // Crear kilometrajes asociados a vehiculo1
        for (int i = 1; i <= 5; i++) {
            KilometrajeVehiculo kilometraje = KilometrajeVehiculo.builder()
                    .kilometrosRecorridos(100 * (float) i) // Convertir i a Float
                    .kilometroAlFinTrayecto(1000 + (100 * (float) i))
                    .kilometroInicioTrayecto(900 + (100 * (float) i))
                    .fechaInicio(LocalDate.now().minusDays(i))
                    .fechaFin(LocalDate.now().minusDays(i - 1))
                    .vehiculo(vehiculo1)
                    .build();
            kilometrajeVehiculoRepository.save(kilometraje);
        }

    }
}
