package psp.videojuegosmondodb.service;

import org.springframework.stereotype.Service;
import psp.videojuegosmondodb.dto.DesarrolladorDTO;
import psp.videojuegosmondodb.exception.DuplicateResourceException;
import psp.videojuegosmondodb.exception.ResourceNotFoundException;
import psp.videojuegosmondodb.model.Desarrollador;
import psp.videojuegosmondodb.repository.DesarrolladorRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para operaciones con desarrolladores
 */
@Service
public class DesarrolladorService {

    private final DesarrolladorRepository desarrolladorRepository;

    public DesarrolladorService(DesarrolladorRepository desarrolladorRepository) {
        this.desarrolladorRepository = desarrolladorRepository;
    }

    /**
     * Obtiene todos los desarrolladores
     * @return lista de desarrolladores
     */
    public List<DesarrolladorDTO> obtenerTodos() {
        return desarrolladorRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un desarrollador por su ID
     * @param id ID del desarrollador
     * @return el desarrollador
     */
    public DesarrolladorDTO obtenerPorId(String id) {
        Desarrollador desarrollador = desarrolladorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Desarrollador", "id", id));
        return mapToDTO(desarrollador);
    }

    /**
     * Crea un nuevo desarrollador
     * @param dto datos del nuevo desarrollador
     * @return el desarrollador creado
     */
    public DesarrolladorDTO crear(DesarrolladorDTO dto) {
        // Verificar si ya existe un desarrollador con ese nombre de estudio
        if (desarrolladorRepository.existsByNombreEstudio(dto.getNombreEstudio())) {
            throw new DuplicateResourceException("Ya existe un desarrollador con el nombre de estudio: " + dto.getNombreEstudio());
        }
        
        Desarrollador desarrollador = mapToEntity(dto);
        Desarrollador desarrolladorGuardado = desarrolladorRepository.save(desarrollador);
        return mapToDTO(desarrolladorGuardado);
    }

    /**
     * Actualiza un desarrollador existente
     * @param id ID del desarrollador
     * @param dto datos actualizados
     * @return el desarrollador actualizado
     */
    public DesarrolladorDTO actualizar(String id, DesarrolladorDTO dto) {
        Desarrollador desarrollador = desarrolladorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Desarrollador", "id", id));
        
        // Verificar si el nombre de estudio ya está en uso por otro desarrollador
        if (!desarrollador.getNombreEstudio().equals(dto.getNombreEstudio()) && 
                desarrolladorRepository.existsByNombreEstudio(dto.getNombreEstudio())) {
            throw new DuplicateResourceException("Ya existe un desarrollador con el nombre de estudio: " + dto.getNombreEstudio());
        }
        
        desarrollador.setNombreEstudio(dto.getNombreEstudio());
        desarrollador.setPaisOrigen(dto.getPaisOrigen());
        desarrollador.setAnoFundacion(dto.getAnoFundacion());
        
        Desarrollador desarrolladorActualizado = desarrolladorRepository.save(desarrollador);
        return mapToDTO(desarrolladorActualizado);
    }

    /**
     * Elimina un desarrollador
     * @param id ID del desarrollador
     */
    public void eliminar(String id) {
        if (!desarrolladorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Desarrollador", "id", id);
        }
        desarrolladorRepository.deleteById(id);
    }

    /**
     * Convierte una entidad Desarrollador a DTO
     * @param desarrollador entidad Desarrollador
     * @return DTO de Desarrollador
     */
    private DesarrolladorDTO mapToDTO(Desarrollador desarrollador) {
        DesarrolladorDTO dto = new DesarrolladorDTO();
        dto.setId(desarrollador.getId());
        dto.setNombreEstudio(desarrollador.getNombreEstudio());
        dto.setPaisOrigen(desarrollador.getPaisOrigen());
        dto.setAnoFundacion(desarrollador.getAnoFundacion());
        return dto;
    }

    /**
     * Convierte un DTO a entidad Desarrollador
     * @param dto DTO de Desarrollador
     * @return entidad Desarrollador
     */
    public Desarrollador mapToEntity(DesarrolladorDTO dto) {
        Desarrollador desarrollador = new Desarrollador();
        if (dto.getId() != null) {
            desarrollador.setId(dto.getId());
        }
        desarrollador.setNombreEstudio(dto.getNombreEstudio());
        desarrollador.setPaisOrigen(dto.getPaisOrigen());
        desarrollador.setAnoFundacion(dto.getAnoFundacion());
        return desarrollador;
    }
}