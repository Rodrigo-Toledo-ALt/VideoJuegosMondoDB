package psp.videojuegosmondodb.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO para transferencia de datos de videojuegos
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideojuegoDTO {

    private String id;
    
    @NotBlank(message = "El título es obligatorio")
    private String titulo;
    
    @NotBlank(message = "El ID del desarrollador es obligatorio")
    private String desarrolladorId;
    
    private String desarrolladorNombre;
    
    @NotBlank(message = "El ID del género es obligatorio")
    private String generoId;
    
    private String generoNombre;
    
    @NotBlank(message = "La plataforma es obligatoria")
    private String plataforma;
    
    @NotNull(message = "La fecha de lanzamiento es obligatoria")
    @PastOrPresent(message = "La fecha de lanzamiento debe ser en el pasado o presente")
    private LocalDate fechaLanzamiento;
    
    @NotBlank(message = "La calificación PEGI es obligatoria")
    private String calificacionPEGI;
}