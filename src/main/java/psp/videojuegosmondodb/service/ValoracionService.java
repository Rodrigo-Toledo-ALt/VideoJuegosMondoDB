package psp.videojuegosmondodb.service;

import org.springframework.stereotype.Service;
import psp.videojuegosmondodb.dto.ValoracionDTO;
import psp.videojuegosmondodb.exception.BadRequestException;
import psp.videojuegosmondodb.exception.DuplicateResourceException;
import psp.videojuegosmondodb.exception.ResourceNotFoundException;
import psp.videojuegosmondodb.model.Usuario;
import psp.videojuegosmondodb.model.Valoracion;
import psp.videojuegosmondodb.model.Videojuego;
import psp.videojuegosmondodb.repository.UsuarioRepository;
import psp.videojuegosmondodb.repository.ValoracionRepository;
import psp.videojuegosmondodb.repository.VideojuegoRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para operaciones con valoraciones
 */
@Service
public class ValoracionService {

    private final ValoracionRepository valoracionRepository;
    private final UsuarioRepository usuarioRepository;
    private final VideojuegoRepository videojuegoRepository;

    public ValoracionService(ValoracionRepository valoracionRepository,
                            UsuarioRepository usuarioRepository,
                            VideojuegoRepository videojuegoRepository) {
        this.valoracionRepository = valoracionRepository;
        this.usuarioRepository = usuarioRepository;
        this.videojuegoRepository = videojuegoRepository;
    }

    /**
     * Obtiene todas las valoraciones de un videojuego
     * @param videojuegoId ID del videojuego
     * @return lista de valoraciones
     */
    public List<ValoracionDTO> obtenerPorVideojuego(String videojuegoId) {
        Videojuego videojuego = videojuegoRepository.findById(videojuegoId)
                .orElseThrow(() -> new ResourceNotFoundException("Videojuego", "id", videojuegoId));
        
        return valoracionRepository.findByVideojuego(videojuego).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Crea una nueva valoración
     * @param dto datos de la nueva valoración
     * @param usuarioId ID del usuario que crea la valoración
     * @return la valoración creada
     */
    public ValoracionDTO crear(ValoracionDTO dto, String usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", usuarioId));
        
        Videojuego videojuego = videojuegoRepository.findById(dto.getVideojuegoId())
                .orElseThrow(() -> new ResourceNotFoundException("Videojuego", "id", dto.getVideojuegoId()));
        
        // Verificar si el usuario ya ha valorado este videojuego
        if (valoracionRepository.existsByUsuarioAndVideojuego(usuario, videojuego)) {
            throw new DuplicateResourceException("Ya has valorado este videojuego");
        }
        
        // Validar puntuación
        if (dto.getPuntuacion() < 1 || dto.getPuntuacion() > 10) {
            throw new BadRequestException("La puntuación debe estar entre 1 y 10");
        }
        
        Valoracion valoracion = new Valoracion();
        valoracion.setUsuario(usuario);
        valoracion.setVideojuego(videojuego);
        valoracion.setPuntuacion(dto.getPuntuacion());
        valoracion.setComentario(dto.getComentario());
        
        Valoracion valoracionGuardada = valoracionRepository.save(valoracion);
        return mapToDTO(valoracionGuardada);
    }

    /**
     * Convierte una entidad Valoracion a DTO
     * @param valoracion entidad Valoracion
     * @return DTO de Valoracion
     */
    private ValoracionDTO mapToDTO(Valoracion valoracion) {
        ValoracionDTO dto = new ValoracionDTO();
        dto.setId(valoracion.getId());
        
        if (valoracion.getUsuario() != null) {
            dto.setUsuarioId(valoracion.getUsuario().getId());
            dto.setUsuarioNombre(valoracion.getUsuario().getNombre());
        }
        
        if (valoracion.getVideojuego() != null) {
            dto.setVideojuegoId(valoracion.getVideojuego().getId());
            dto.setVideojuegoTitulo(valoracion.getVideojuego().getTitulo());
        }
        
        dto.setPuntuacion(valoracion.getPuntuacion());
        dto.setComentario(valoracion.getComentario());
        
        return dto;
    }
}