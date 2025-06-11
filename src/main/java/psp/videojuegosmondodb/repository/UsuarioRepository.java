package psp.videojuegosmondodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import psp.videojuegosmondodb.model.Usuario;

import java.util.Optional;

/**
 * Repositorio para operaciones CRUD con usuarios
 */
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    
    /**
     * Busca un usuario por su email
     * @param email el email del usuario
     * @return el usuario encontrado o vacío
     */
    Optional<Usuario> findByEmail(String email);
    
    /**
     * Verifica si existe un usuario con el email especificado
     * @param email el email a verificar
     * @return true si existe, false en caso contrario
     */
    boolean existsByEmail(String email);
}