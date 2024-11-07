package com.gifa_api.controller;

import com.gifa_api.dto.chofer.AsignarChoferDTO;
import com.gifa_api.dto.chofer.ChoferRegistroDTO;
import com.gifa_api.exception.BadRoleException;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.Usuario;
import com.gifa_api.service.IChoferService;
import com.gifa_api.utils.enums.Rol;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chofer")
@RequiredArgsConstructor
public class ChoferController {
    private final IChoferService choferService;

    @PostMapping("/registrar")
    public ResponseEntity<?> createChofer(@RequestBody ChoferRegistroDTO choferRegistroDTO) {
        choferService.registro(choferRegistroDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PatchMapping("/asignarChofer")
    public ResponseEntity<?> AsignarChofer(@RequestBody AsignarChoferDTO asignarChoferDTO) {
        choferService.asignarVehiculo(asignarChoferDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/verChoferes")
    public ResponseEntity<?> getAllChofers() {
        return new ResponseEntity<>(choferService.obtenerAll(),HttpStatus.OK);
    }

    @GetMapping("/verVehiculo")
    public ResponseEntity<?> getVerVehiculo() {
        Usuario u =  getUserFromToken();
        return new ResponseEntity<>(choferService.obtenerVehiculo(u.getId()),HttpStatus.OK);

    }


    private Usuario getUserFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication != null && authentication.isAuthenticated())) {
            throw new NotFoundException("El token no corresponde a un usuario.");
        }

        Usuario usuario = (Usuario) authentication.getPrincipal();
        if (usuario.getRol() != Rol.CHOFER) {
            throw new BadRoleException("El usuario no corresponde a un operador.");
        }

        return usuario;
    }
}
