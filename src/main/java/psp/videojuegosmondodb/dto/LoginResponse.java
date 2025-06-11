package psp.videojuegosmondodb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import psp.videojuegosmondodb.model.Rol;

/**
 * DTO para la respuesta de inicio de sesión
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String id;
    private String nombre;
    private String email;
    private Rol rol;
    private String token;
}