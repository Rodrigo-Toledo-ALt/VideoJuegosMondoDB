package psp.videojuegosmondodb.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;

/**
 * DTO para transferencia de datos de desarrolladores
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DesarrolladorDTO {

    private String id;
    
    @NotBlank(message = "El nombre del estudio es obligatorio")
    private String nombreEstudio;
    
    @NotBlank(message = "El país de origen es obligatorio")
    private String paisOrigen;
    
    @NotNull(message = "El año de fundación es obligatorio")
    @PastOrPresent(message = "El año de fundación debe ser en el pasado o presente")
    private Year anoFundacion;
}