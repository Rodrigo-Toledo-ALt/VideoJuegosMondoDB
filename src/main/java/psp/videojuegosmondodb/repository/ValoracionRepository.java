package psp.videojuegosmondodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import psp.videojuegosmondodb.model.Usuario;
import psp.videojuegosmondodb.model.Valoracion;
import psp.videojuegosmondodb.model.Videojuego;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para operaciones CRUD con valoraciones
 */
public interface ValoracionRepository extends MongoRepository<Valoracion, String> {
    
    /**
     * Busca todas las valoraciones de un videojuego específico
     * @param videojuego el videojuego
     * @return lista de valoraciones del videojuego
     */
    List<Valoracion> findByVideojuego(Videojuego videojuego);
    
    /**
     * Busca todas las valoraciones realizadas por un usuario
     * @param usuario el usuario
     * @return lista de valoraciones del usuario
     */
    List<Valoracion> findByUsuario(Usuario usuario);
    
    /**
     * Busca la valoración de un usuario para un videojuego específico
     * @param usuario el usuario
     * @param videojuego el videojuego
     * @return la valoración encontrada o vacío
     */
    Optional<Valoracion> findByUsuarioAndVideojuego(Usuario usuario, Videojuego videojuego);
    
    /**
     * Verifica si existe una valoración de un usuario para un videojuego específico
     * @param usuario el usuario
     * @param videojuego el videojuego
     * @return true si existe, false en caso contrario
     */
    boolean existsByUsuarioAndVideojuego(Usuario usuario, Videojuego videojuego);
}