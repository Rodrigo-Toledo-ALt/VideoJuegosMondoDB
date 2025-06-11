package psp.videojuegosmondodb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import psp.videojuegosmondodb.model.Desarrollador;
import psp.videojuegosmondodb.model.Genero;
import psp.videojuegosmondodb.model.Videojuego;

import java.util.List;

/**
 * Repositorio para operaciones CRUD con videojuegos
 */
public interface VideojuegoRepository extends MongoRepository<Videojuego, String> {
    
    /**
     * Busca videojuegos cuyo título contenga el texto especificado (case-insensitive)
     * @param titulo texto a buscar en el título
     * @return lista de videojuegos que coinciden
     */
    List<Videojuego> findByTituloContainingIgnoreCase(String titulo);
    
    /**
     * Busca videojuegos cuyo título contenga el texto especificado (case-insensitive) con paginación
     * @param titulo texto a buscar en el título
     * @param pageable configuración de paginación
     * @return página de videojuegos que coinciden
     */
    Page<Videojuego> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);
    
    /**
     * Busca videojuegos por género
     * @param genero el género a buscar
     * @return lista de videojuegos del género especificado
     */
    List<Videojuego> findByGenero(Genero genero);
    
    /**
     * Busca videojuegos por plataforma
     * @param plataforma la plataforma a buscar
     * @return lista de videojuegos de la plataforma especificada
     */
    List<Videojuego> findByPlataforma(String plataforma);
    
    /**
     * Busca videojuegos por desarrollador
     * @param desarrollador el desarrollador a buscar
     * @return lista de videojuegos del desarrollador especificado
     */
    List<Videojuego> findByDesarrollador(Desarrollador desarrollador);
    
    /**
     * Busca videojuegos que cumplan con los filtros especificados
     * @param genero el género (opcional)
     * @param plataforma la plataforma (opcional)
     * @param desarrollador el desarrollador (opcional)
     * @return lista de videojuegos que cumplen con los filtros
     */
    @Query("{ $and: [ " +
           "?#{ [0] == null ? { $expr: true } : { 'genero.$id' : [0]._id } }, " +
           "?#{ [1] == null ? { $expr: true } : { 'plataforma' : [1] } }, " +
           "?#{ [2] == null ? { $expr: true } : { 'desarrollador.$id' : [2]._id } } " +
           "] }")
    List<Videojuego> findByFilters(Genero genero, String plataforma, Desarrollador desarrollador);
}