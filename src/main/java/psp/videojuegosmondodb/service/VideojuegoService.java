package psp.videojuegosmondodb.service;

import org.springframework.stereotype.Service;
import psp.videojuegosmondodb.dto.VideojuegoDTO;
import psp.videojuegosmondodb.exception.ResourceNotFoundException;
import psp.videojuegosmondodb.model.Desarrollador;
import psp.videojuegosmondodb.model.Genero;
import psp.videojuegosmondodb.model.Videojuego;
import psp.videojuegosmondodb.repository.DesarrolladorRepository;
import psp.videojuegosmondodb.repository.GeneroRepository;
import psp.videojuegosmondodb.repository.VideojuegoRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para operaciones con videojuegos
 */
@Service
public class VideojuegoService {

    private final VideojuegoRepository videojuegoRepository;
    private final GeneroRepository generoRepository;
    private final DesarrolladorRepository desarrolladorRepository;

    public VideojuegoService(VideojuegoRepository videojuegoRepository,
                             GeneroRepository generoRepository,
                             DesarrolladorRepository desarrolladorRepository) {
        this.videojuegoRepository = videojuegoRepository;
        this.generoRepository = generoRepository;
        this.desarrolladorRepository = desarrolladorRepository;
    }

    /**
     * Obtiene todos los videojuegos
     * @return lista de videojuegos
     */
    public List<VideojuegoDTO> obtenerTodos() {
        return videojuegoRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un videojuego por su ID
     * @param id ID del videojuego
     * @return el videojuego
     */
    public VideojuegoDTO obtenerPorId(String id) {
        Videojuego videojuego = videojuegoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Videojuego", "id", id));
        return mapToDTO(videojuego);
    }

    /**
     * Busca videojuegos por título
     * @param titulo texto a buscar en el título
     * @return lista de videojuegos que coinciden
     */
    public List<VideojuegoDTO> buscarPorTitulo(String titulo) {
        return videojuegoRepository.findByTituloContainingIgnoreCase(titulo).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Filtra videojuegos por género, plataforma y/o desarrollador
     * Acepta tanto IDs como nombres para género y desarrollador
     * @param generoParam nombre del género (opcional)
     * @param plataforma plataforma (opcional)
     * @param desarrolladorParam nombre del desarrollador (opcional)
     * @return lista de videojuegos filtrados
     */
    public List<VideojuegoDTO> filtrar(String generoParam, String plataforma, String desarrolladorParam) {
        Genero genero = null;
        Desarrollador desarrollador = null;

        // Buscar género por nombre si se proporciona
        if (generoParam != null && !generoParam.isEmpty()) {
            genero = generoRepository.findByNombre(generoParam)
                    .orElseThrow(() -> new ResourceNotFoundException("Género", "nombre", generoParam));
        }

        // Buscar desarrollador por nombre si se proporciona
        if (desarrolladorParam != null && !desarrolladorParam.isEmpty()) {
            desarrollador = desarrolladorRepository.findByNombreEstudio(desarrolladorParam)
                    .orElseThrow(() -> new ResourceNotFoundException("Desarrollador", "nombre", desarrolladorParam));
        }

        // Aplicar filtros según los parámetros disponibles
        List<Videojuego> videojuegos;

        if (genero != null && plataforma != null && desarrollador != null) {
            // Todos los filtros
            videojuegos = videojuegoRepository.findByGeneroAndPlataformaAndDesarrollador(genero, plataforma, desarrollador);
        } else if (genero != null && plataforma != null) {
            // Género y plataforma
            videojuegos = videojuegoRepository.findByGeneroAndPlataforma(genero, plataforma);
        } else if (genero != null && desarrollador != null) {
            // Género y desarrollador
            videojuegos = videojuegoRepository.findByGeneroAndDesarrollador(genero, desarrollador);
        } else if (plataforma != null && desarrollador != null) {
            // Plataforma y desarrollador
            videojuegos = videojuegoRepository.findByPlataformaAndDesarrollador(plataforma, desarrollador);
        } else if (genero != null) {
            // Solo género
            videojuegos = videojuegoRepository.findByGenero(genero);
        } else if (plataforma != null) {
            // Solo plataforma
            videojuegos = videojuegoRepository.findByPlataforma(plataforma);
        } else if (desarrollador != null) {
            // Solo desarrollador
            videojuegos = videojuegoRepository.findByDesarrollador(desarrollador);
        } else {
            // Sin filtros, devolver todos
            videojuegos = videojuegoRepository.findAll();
        }

        return videojuegos.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Crea un nuevo videojuego
     * @param dto datos del nuevo videojuego
     * @return el videojuego creado
     */
    public VideojuegoDTO crear(VideojuegoDTO dto) {
        Videojuego videojuego = mapToEntity(dto);
        Videojuego videojuegoGuardado = videojuegoRepository.save(videojuego);
        return mapToDTO(videojuegoGuardado);
    }

    /**
     * Actualiza un videojuego existente
     * @param id ID del videojuego
     * @param dto datos actualizados
     * @return el videojuego actualizado
     */
    public VideojuegoDTO actualizar(String id, VideojuegoDTO dto) {
        if (!videojuegoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Videojuego", "id", id);
        }

        Videojuego videojuego = mapToEntity(dto);
        videojuego.setId(id);
        Videojuego videojuegoActualizado = videojuegoRepository.save(videojuego);
        return mapToDTO(videojuegoActualizado);
    }

    /**
     * Elimina un videojuego
     * @param id ID del videojuego
     */
    public void eliminar(String id) {
        if (!videojuegoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Videojuego", "id", id);
        }
        videojuegoRepository.deleteById(id);
    }

    /**
     * Convierte una entidad Videojuego a DTO
     * @param videojuego entidad Videojuego
     * @return DTO de Videojuego
     */
    private VideojuegoDTO mapToDTO(Videojuego videojuego) {
        VideojuegoDTO dto = new VideojuegoDTO();
        dto.setId(videojuego.getId());
        dto.setTitulo(videojuego.getTitulo());
        dto.setPlataforma(videojuego.getPlataforma());
        dto.setFechaLanzamiento(videojuego.getFechaLanzamiento());
        dto.setCalificacionPEGI(videojuego.getCalificacionPEGI());
        dto.setImagenURL(videojuego.getImagenURL()); // Campo imagenURL agregado

        if (videojuego.getGenero() != null) {
            dto.setGeneroId(videojuego.getGenero().getId());
            dto.setGeneroNombre(videojuego.getGenero().getNombre());
        }

        if (videojuego.getDesarrollador() != null) {
            dto.setDesarrolladorId(videojuego.getDesarrollador().getId());
            dto.setDesarrolladorNombre(videojuego.getDesarrollador().getNombreEstudio());
        }

        return dto;
    }

    /**
     * Convierte un DTO a entidad Videojuego
     * @param dto DTO de Videojuego
     * @return entidad Videojuego
     */
    private Videojuego mapToEntity(VideojuegoDTO dto) {
        Videojuego videojuego = new Videojuego();
        if (dto.getId() != null) {
            videojuego.setId(dto.getId());
        }
        videojuego.setTitulo(dto.getTitulo());
        videojuego.setPlataforma(dto.getPlataforma());
        videojuego.setFechaLanzamiento(dto.getFechaLanzamiento());
        videojuego.setCalificacionPEGI(dto.getCalificacionPEGI());
        videojuego.setImagenURL(dto.getImagenURL()); // Campo imagenURL agregado

        // Obtener género por ID
        if (dto.getGeneroId() != null && !dto.getGeneroId().isEmpty()) {
            Genero genero = generoRepository.findById(dto.getGeneroId())
                    .orElseThrow(() -> new ResourceNotFoundException("Género", "id", dto.getGeneroId()));
            videojuego.setGenero(genero);
        }

        // Obtener desarrollador por ID
        if (dto.getDesarrolladorId() != null && !dto.getDesarrolladorId().isEmpty()) {
            Desarrollador desarrollador = desarrolladorRepository.findById(dto.getDesarrolladorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Desarrollador", "id", dto.getDesarrolladorId()));
            videojuego.setDesarrollador(desarrollador);
        }

        return videojuego;
    }
}