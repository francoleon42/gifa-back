package com.gifa_api.testUnitario.service;

import com.gifa_api.config.jwt.JwtService;
import com.gifa_api.dto.login.LoginRequestDTO;
import com.gifa_api.dto.login.LoginResponseDTO;
import com.gifa_api.dto.login.RegisterRequestDTO;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.exception.RegisterException;
import com.gifa_api.model.Usuario;
import com.gifa_api.repository.IUsuarioRepository;
import com.gifa_api.service.impl.AuthServiceImpl;
import com.gifa_api.utils.enums.Rol;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AuthServiceImplTest {

    @Mock
    private IUsuarioRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthServiceImpl authService;

    private final String password = "1234"; // Contraseña fija
    private final String encodedPassword = "encodedPassword"; // Simulación de la contraseña encriptada

    private final String mockedJwtToken = "mockedJwtToken";

    // Usuarios
    private final String adminUsername = "admin";
    private final String supervisorUsername = "supervisor";
    private final String operadorUsername = "operador";
    private final String gerenteUsername = "gerente";

    @Test
    void testLogin_Success_Admin() {
        loginTestHelper(adminUsername, Rol.ADMINISTRADOR);
    }

    @Test
    void testLogin_Success_Supervisor() {
        loginTestHelper(supervisorUsername, Rol.SUPERVISOR);
    }

    @Test
    void testLogin_Success_Operador() {
        loginTestHelper(operadorUsername, Rol.OPERADOR);
    }

    @Test
    void testLogin_Success_Gerente() {
        loginTestHelper(gerenteUsername, Rol.GERENTE);
    }

    @Test
    void testRegister_Success_Admin() {
        registerTestHelper(adminUsername, Rol.ADMINISTRADOR);
    }

    @Test
    void testRegister_Success_Supervisor() {
        registerTestHelper(supervisorUsername, Rol.SUPERVISOR);
    }

    @Test
    void testRegister_Success_Operador() {
        registerTestHelper(operadorUsername, Rol.OPERADOR);
    }

    @Test
    void testRegister_Success_Gerente() {
        registerTestHelper(gerenteUsername, Rol.GERENTE);
    }

    private void loginTestHelper(String username, Rol role) {
        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setUsername(username);
        loginRequest.setPassword(password);
        Usuario user = new Usuario();
        user.setUsuario(username);
        user.setContrasena(encodedPassword);
        user.setRol(role);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null); // Simular autenticación exitosa
        when(userRepository.findByUsuario(username)).thenReturn(Optional.of(user));
        when(jwtService.getToken(user)).thenReturn(mockedJwtToken);

        LoginResponseDTO response = authService.login(loginRequest);

        assertEquals(username, response.getUsername());
        assertEquals(mockedJwtToken, response.getToken());
        assertEquals(role, response.getRole());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository).findByUsuario(username);
        verify(jwtService).getToken(user);
    }

    private void registerTestHelper(String username, Rol role) {
        RegisterRequestDTO registerRequest = new RegisterRequestDTO();
        registerRequest.setUsername(username);
        registerRequest.setPassword(password);
        registerRequest.setRole(role.name());
        Usuario user = new Usuario();
        user.setUsuario(username);
        user.setContrasena(password);
        user.setRol(role);

        when(userRepository.existsByUsuario(username)).thenReturn(false);
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(jwtService.getToken(any(Usuario.class))).thenReturn(mockedJwtToken);


        LoginResponseDTO response = authService.register(registerRequest);

        assertEquals(username, response.getUsername());
        assertEquals(mockedJwtToken, response.getToken());
        assertEquals(role, response.getRole());
        verify(userRepository).save(any(Usuario.class));
    }

    @Test
    void testLoginUsuarioNoExiste() {
        String usuarioInexistente = "unknown";
        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setUsername(usuarioInexistente);
        loginRequest.setPassword(password);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null); // Simular autenticación NO exitosa
        when(userRepository.findByUsuario(usuarioInexistente)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> authService.login(loginRequest));
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository).findByUsuario(usuarioInexistente);
    }

    @Test
    void testRegisterUsuarioYaExiste() {
        String usuarioAdmin = adminUsername;
        RegisterRequestDTO registerRequest = new RegisterRequestDTO(usuarioAdmin, password, Rol.ADMINISTRADOR.toString());

        when(userRepository.existsByUsuario(usuarioAdmin)).thenReturn(true);

        assertThrows(RegisterException.class, () -> authService.register(registerRequest));
        verify(userRepository, never()).save(any(Usuario.class));
    }
}