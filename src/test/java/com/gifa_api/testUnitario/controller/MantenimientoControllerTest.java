package com.gifa_api.testUnitario.controller;

import com.gifa_api.controller.MantenimientoController;
import com.gifa_api.dto.mantenimiento.FinalizarMantenimientoDTO;
import com.gifa_api.dto.mantenimiento.MantenimientosResponseDTO;
import com.gifa_api.dto.mantenimiento.RegistrarMantenimientoDTO;

import com.gifa_api.service.IMantenimientoService;
import com.gifa_api.service.IitemUsadoMantenimientoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MantenimientoControllerTest {

    @InjectMocks
    private MantenimientoController mantenimientoController;

    @Mock
    private IMantenimientoService mantenimientoService;

    @Mock
    private IitemUsadoMantenimientoService itemUsadoMantenimientoService;

//    @Mock
//    private Usuario usuario;


    @Test
    void testCargarMantenimientoManualmente() {
        RegistrarMantenimientoDTO registrarMantenimientoDTO = new RegistrarMantenimientoDTO();
        // Configura registrarMantenimientoDTO según sea necesario

        ResponseEntity<?> response = mantenimientoController.cargarMantenimientoManualmente(registrarMantenimientoDTO);

        verify(mantenimientoService, times(1)).crearMantenimiento(registrarMantenimientoDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testVerMantenimientoPorVehiculo() {
        Integer vehiculoId = 1;
        MantenimientosResponseDTO mantenimientosResponseDTO = new MantenimientosResponseDTO();
        when(mantenimientoService.verMantenimientosPorVehiculo(vehiculoId)).thenReturn(mantenimientosResponseDTO);

        ResponseEntity<MantenimientosResponseDTO> response = mantenimientoController.verMantenimientoPorVehiculo(vehiculoId);

        verify(mantenimientoService, times(1)).verMantenimientosPorVehiculo(vehiculoId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mantenimientosResponseDTO, response.getBody());
    }

    @Test
    void testVerMantenimientosFinalizados() {
        MantenimientosResponseDTO mantenimientosResponseDTO = new MantenimientosResponseDTO();
        when(mantenimientoService.verMantenimientosFinalizados()).thenReturn(mantenimientosResponseDTO);

        ResponseEntity<MantenimientosResponseDTO> response = mantenimientoController.verMantenimientosFinalizados();

        verify(mantenimientoService, times(1)).verMantenimientosFinalizados();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mantenimientosResponseDTO, response.getBody());
    }

    @Test
    void testVerMantenimientosPendientes() {
        MantenimientosResponseDTO mantenimientosResponseDTO = new MantenimientosResponseDTO();
        when(mantenimientoService.verMantenimientosPendientes()).thenReturn(mantenimientosResponseDTO);

        ResponseEntity<MantenimientosResponseDTO> response = mantenimientoController.verMantenimientosPendientes();

        verify(mantenimientoService, times(1)).verMantenimientosPendientes();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mantenimientosResponseDTO, response.getBody());
    }

    @Test
    void testFinalizarMantenimiento() {
        Integer mantenimientoId = 1;
        FinalizarMantenimientoDTO finalizarMantenimientoDTO = new FinalizarMantenimientoDTO();

        ResponseEntity<?> response = mantenimientoController.finalizarMantenimiento(mantenimientoId, finalizarMantenimientoDTO);

        verify(mantenimientoService, times(1)).finalizarMantenimiento(mantenimientoId);
        verify(itemUsadoMantenimientoService, times(1)).agregaritemUtilizadoEnMantenimiento(mantenimientoId, finalizarMantenimientoDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

//    @Test
//    void testAsignarMantenimiento() {
//        Integer mantenimientoId = 1;
//
//        // Mockea el rol y el ID del usuario
//        when(usuario.getRol()).thenReturn(Rol.OPERADOR);
//        when(usuario.getId()).thenReturn(1);
//
//        // Mockea la invocación del método getUserFromToken para devolver el usuario mockeado
//        when(mantenimientoController.getUserFromToken()).thenReturn(usuario); // Asegúrate de que este método esté disponible
//
//        ResponseEntity<?> response = mantenimientoController.asignarMantenimiento(mantenimientoId);
//
//        // Verifica que el servicio haya sido llamado correctamente
//        verify(mantenimientoService, times(1)).asignarMantenimiento(mantenimientoId, usuario);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }

//    @Test
//    void testVerMantenimientosDeOperador() {
//        MantenimientosResponseDTO mantenimientosResponseDTO = new MantenimientosResponseDTO();
//        mantenimientosResponseDTO.setMantenimientos(Collections.emptyList()); // Inicializa la lista vacía
//
//        when(usuario.getId()).thenReturn(1);
//        when(mantenimientoService.obtenerMantenimientosPorOperador(1)).thenReturn(mantenimientosResponseDTO);
//
//        ResponseEntity<MantenimientosResponseDTO> response = mantenimientoController.verMantenimientosDeOperador();
//
//        verify(mantenimientoService, times(1)).obtenerMantenimientosPorOperador(1);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(mantenimientosResponseDTO, response.getBody());
//    }

//    @Test
//    void testGetUserFromTokenThrowsNotFoundException() {
//        when(usuario.getRol()).thenThrow(new NotFoundException("El token no corresponde a un usuario."));
//
//        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
//            mantenimientoController.verMantenimientosDeOperador();
//        });
//
//        assertEquals("El token no corresponde a un usuario.", exception.getMessage());
//    }
//
//    @Test
//    void testGetUserFromTokenThrowsBadRoleException() {
//        when(usuario.getRol()).thenReturn(Rol.ADMINISTRADOR);
//        when(usuario.getId()).thenReturn(1);
//
//        BadRoleException exception = assertThrows(BadRoleException.class, () -> {
//            mantenimientoController.verMantenimientosDeOperador();
//        });
//
//        assertEquals("El usuario no corresponde a un operador.", exception.getMessage());
//    }
}
