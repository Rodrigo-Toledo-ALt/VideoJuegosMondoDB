package psp.videojuegosmondodb.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entidad que representa una valoración de un usuario sobre un videojuego
 */
@Document(collection = "valoraciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@CompoundIndex(def = "{'usuario': 1, 'videojuego': 1}", unique = true)
public class Valoracion {

    @Id
    private String id;

    @DBRef
    @NotNull(message = "El usuario es obligatorio")
    private Usuario usuario;

    @DBRef
    @NotNull(message = "El videojuego es obligatorio")
    private Videojuego videojuego;

    @NotNull(message = "La puntuación es obligatoria")
    @Min(value = 1, message = "La puntuación mínima es 1")
    @Max(value = 10, message = "La puntuación máxima es 10")
    private Integer puntuacion;

    private String comentario;
}