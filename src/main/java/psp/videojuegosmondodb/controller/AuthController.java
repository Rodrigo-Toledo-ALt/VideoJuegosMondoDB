package psp.videojuegosmondodb.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import psp.videojuegosmondodb.dto.LoginRequest;
import psp.videojuegosmondodb.dto.LoginResponse;
import psp.videojuegosmondodb.dto.RegistroRequest;
import psp.videojuegosmondodb.dto.UsuarioDTO;
import psp.videojuegosmondodb.service.AuthService;

/**
 * Controlador para autenticación y registro
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Inicia sesión y devuelve un token JWT
     * @param loginRequest datos de inicio de sesión
     * @return respuesta con token y datos del usuario
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    /**
     * Registra un nuevo usuario
     * @param registroRequest datos de registro
     * @return datos del usuario registrado
     */
    @PostMapping("/register")
    public ResponseEntity<UsuarioDTO> registro(@Valid @RequestBody RegistroRequest registroRequest) {
        return ResponseEntity.ok(authService.registro(registroRequest));
    }
}