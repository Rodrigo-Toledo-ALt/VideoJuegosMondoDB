package psp.videojuegosmondodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import psp.videojuegosmondodb.model.Genero;

import java.util.Optional;

/**
 * Repositorio para operaciones CRUD con géneros
 */
public interface GeneroRepository extends MongoRepository<Genero, String> {
    
    /**
     * Busca un género por su nombre
     * @param nombre el nombre del género
     * @return el género encontrado o vacío
     */
    Optional<Genero> findByNombre(String nombre);
    
    /**
     * Verifica si existe un género con el nombre especificado
     * @param nombre el nombre a verificar
     * @return true si existe, false en caso contrario
     */
    boolean existsByNombre(String nombre);
}