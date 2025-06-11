package psp.videojuegosmondodb.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Year;

/**
 * Entidad que representa a un desarrollador de videojuegos
 */
@Document(collection = "desarrolladores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Desarrollador {

    @Id
    private String id;

    @NotBlank(message = "El nombre del estudio es obligatorio")
    private String nombreEstudio;

    @NotBlank(message = "El país de origen es obligatorio")
    private String paisOrigen;

    @NotNull(message = "El año de fundación es obligatorio")
    @PastOrPresent(message = "El año de fundación debe ser en el pasado o presente")
    private Year anoFundacion;
}