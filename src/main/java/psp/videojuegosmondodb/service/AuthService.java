package psp.videojuegosmondodb.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import psp.videojuegosmondodb.dto.LoginRequest;
import psp.videojuegosmondodb.dto.LoginResponse;
import psp.videojuegosmondodb.dto.RegistroRequest;
import psp.videojuegosmondodb.dto.UsuarioDTO;
import psp.videojuegosmondodb.model.Usuario;
import psp.videojuegosmondodb.util.JwtUtil;

/**
 * Servicio para autenticación y registro
 */
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;

    public AuthService(AuthenticationManager authenticationManager,
                      UsuarioService usuarioService,
                      JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.usuarioService = usuarioService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Autentica a un usuario y genera un token JWT
     * @param loginRequest datos de inicio de sesión
     * @return respuesta con token y datos del usuario
     */
    public LoginResponse login(LoginRequest loginRequest) {
        // Autenticar credenciales
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        // Obtener usuario y generar token
        Usuario usuario = (Usuario) authentication.getPrincipal();
        String token = jwtUtil.generateToken(usuario);
        
        // Crear respuesta
        return new LoginResponse(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getRol(),
                token
        );
    }

    /**
     * Registra un nuevo usuario
     * @param request datos de registro
     * @return DTO del usuario registrado
     */
    public UsuarioDTO registro(RegistroRequest request) {
        return usuarioService.registrar(request);
    }
}