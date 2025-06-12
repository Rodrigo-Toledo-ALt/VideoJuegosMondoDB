package psp.videojuegosmondodb.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

/**
 * Entidad que representa un videojuego
 */
@Document(collection = "videojuegos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Videojuego {

    @Id
    private String id;

    @NotBlank(message = "El título es obligatorio")
    @Indexed
    private String titulo;

    @DBRef
    @NotNull(message = "El desarrollador es obligatorio")
    private Desarrollador desarrollador;

    @DBRef
    @NotNull(message = "El género es obligatorio")
    private Genero genero;

    @NotBlank(message = "La plataforma es obligatoria")
    private String plataforma;

    @NotNull(message = "La fecha de lanzamiento es obligatoria")
    @PastOrPresent(message = "La fecha de lanzamiento debe ser en el pasado o presente")
    private LocalDate fechaLanzamiento;

    @NotBlank(message = "La calificación PEGI es obligatoria")
    private String calificacionPEGI;

    // Nuevo campo para la URL de la imagen de portada
    private String imagenURL;
}