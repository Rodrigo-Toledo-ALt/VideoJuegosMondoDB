package psp.videojuegosmondodb.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para transferencia de datos de valoraciones
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValoracionDTO {

    private String id;
    
    private String usuarioId;
    
    private String usuarioNombre;
    
    @NotBlank(message = "El ID del videojuego es obligatorio")
    private String videojuegoId;
    
    private String videojuegoTitulo;
    
    @NotNull(message = "La puntuación es obligatoria")
    @Min(value = 1, message = "La puntuación mínima es 1")
    @Max(value = 10, message = "La puntuación máxima es 10")
    private Integer puntuacion;
    
    private String comentario;
}