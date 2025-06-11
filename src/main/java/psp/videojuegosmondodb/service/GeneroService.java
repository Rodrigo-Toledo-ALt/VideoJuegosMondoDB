package psp.videojuegosmondodb.service;

import org.springframework.stereotype.Service;
import psp.videojuegosmondodb.dto.GeneroDTO;
import psp.videojuegosmondodb.exception.DuplicateResourceException;
import psp.videojuegosmondodb.exception.ResourceNotFoundException;
import psp.videojuegosmondodb.model.Genero;
import psp.videojuegosmondodb.repository.GeneroRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para operaciones con géneros
 */
@Service
public class GeneroService {

    private final GeneroRepository generoRepository;

    public GeneroService(GeneroRepository generoRepository) {
        this.generoRepository = generoRepository;
    }

    /**
     * Obtiene todos los géneros
     * @return lista de géneros
     */
    public List<GeneroDTO> obtenerTodos() {
        return generoRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un género por su ID
     * @param id ID del género
     * @return el género
     */
    public GeneroDTO obtenerPorId(String id) {
        Genero genero = generoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Género", "id", id));
        return mapToDTO(genero);
    }

    /**
     * Crea un nuevo género
     * @param dto datos del nuevo género
     * @return el género creado
     */
    public GeneroDTO crear(GeneroDTO dto) {
        // Verificar si ya existe un género con ese nombre
        if (generoRepository.existsByNombre(dto.getNombre())) {
            throw new DuplicateResourceException("Ya existe un género con el nombre: " + dto.getNombre());
        }
        
        Genero genero = new Genero();
        genero.setNombre(dto.getNombre());
        
        Genero generoGuardado = generoRepository.save(genero);
        return mapToDTO(generoGuardado);
    }

    /**
     * Actualiza un género existente
     * @param id ID del género
     * @param dto datos actualizados
     * @return el género actualizado
     */
    public GeneroDTO actualizar(String id, GeneroDTO dto) {
        Genero genero = generoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Género", "id", id));
        
        // Verificar si el nombre ya está en uso por otro género
        if (!genero.getNombre().equals(dto.getNombre()) && generoRepository.existsByNombre(dto.getNombre())) {
            throw new DuplicateResourceException("Ya existe un género con el nombre: " + dto.getNombre());
        }
        
        genero.setNombre(dto.getNombre());
        
        Genero generoActualizado = generoRepository.save(genero);
        return mapToDTO(generoActualizado);
    }

    /**
     * Elimina un género
     * @param id ID del género
     */
    public void eliminar(String id) {
        if (!generoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Género", "id", id);
        }
        generoRepository.deleteById(id);
    }

    /**
     * Convierte una entidad Genero a DTO
     * @param genero entidad Genero
     * @return DTO de Genero
     */
    private GeneroDTO mapToDTO(Genero genero) {
        GeneroDTO dto = new GeneroDTO();
        dto.setId(genero.getId());
        dto.setNombre(genero.getNombre());
        return dto;
    }

    /**
     * Convierte un DTO a entidad Genero
     * @param dto DTO de Genero
     * @return entidad Genero
     */
    public Genero mapToEntity(GeneroDTO dto) {
        Genero genero = new Genero();
        genero.setId(dto.getId());
        genero.setNombre(dto.getNombre());
        return genero;
    }
}