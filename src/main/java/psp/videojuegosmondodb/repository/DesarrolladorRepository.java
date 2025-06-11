package psp.videojuegosmondodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import psp.videojuegosmondodb.model.Desarrollador;

import java.util.Optional;

/**
 * Repositorio para operaciones CRUD con desarrolladores
 */
public interface DesarrolladorRepository extends MongoRepository<Desarrollador, String> {
    
    /**
     * Busca un desarrollador por su nombre de estudio
     * @param nombreEstudio el nombre del estudio
     * @return el desarrollador encontrado o vacío
     */
    Optional<Desarrollador> findByNombreEstudio(String nombreEstudio);
    
    /**
     * Verifica si existe un desarrollador con el nombre de estudio especificado
     * @param nombreEstudio el nombre a verificar
     * @return true si existe, false en caso contrario
     */
    boolean existsByNombreEstudio(String nombreEstudio);
}