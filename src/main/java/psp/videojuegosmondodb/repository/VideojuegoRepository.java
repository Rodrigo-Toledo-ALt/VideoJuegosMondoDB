package psp.videojuegosmondodb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
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
     * Busca videojuegos por género y plataforma
     */
    List<Videojuego> findByGeneroAndPlataforma(Genero genero, String plataforma);

    /**
     * Busca videojuegos por género y desarrollador
     */
    List<Videojuego> findByGeneroAndDesarrollador(Genero genero, Desarrollador desarrollador);

    /**
     * Busca videojuegos por plataforma y desarrollador
     */
    List<Videojuego> findByPlataformaAndDesarrollador(String plataforma, Desarrollador desarrollador);

    /**
     * Busca videojuegos por todos los filtros
     */
    List<Videojuego> findByGeneroAndPlataformaAndDesarrollador(Genero genero, String plataforma, Desarrollador desarrollador);
}