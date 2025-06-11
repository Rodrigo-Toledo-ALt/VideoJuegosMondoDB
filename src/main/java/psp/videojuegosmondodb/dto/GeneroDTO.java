package psp.videojuegosmondodb.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para transferencia de datos de géneros
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneroDTO {

    private String id;
    
    @NotBlank(message = "El nombre del género es obligatorio")
    private String nombre;
}