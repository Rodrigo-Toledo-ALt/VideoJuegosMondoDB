package psp.videojuegosmondodb.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import psp.videojuegosmondodb.dto.VideojuegoDTO;
import psp.videojuegosmondodb.service.VideojuegoService;

import java.util.List;

/**
 * Controlador para operaciones con videojuegos
 */
@RestController
@RequestMapping("/videojuegos")
public class VideojuegoController {

    private final VideojuegoService videojuegoService;

    public VideojuegoController(VideojuegoService videojuegoService) {
        this.videojuegoService = videojuegoService;
    }

    /**
     * Obtiene todos los videojuegos
     * @return lista de videojuegos
     */
    @GetMapping
    public ResponseEntity<List<VideojuegoDTO>> obtenerTodos() {
        return ResponseEntity.ok(videojuegoService.obtenerTodos());
    }

    /**
     * Obtiene un videojuego por su ID
     * @param id ID del videojuego
     * @return el videojuego
     */
    @GetMapping("/{id}")
    public ResponseEntity<VideojuegoDTO> obtenerPorId(@PathVariable String id) {
        return ResponseEntity.ok(videojuegoService.obtenerPorId(id));
    }

    /**
     * Busca videojuegos por título
     * @param titulo texto a buscar en el título
     * @return lista de videojuegos que coinciden
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<VideojuegoDTO>> buscarPorTitulo(@RequestParam String titulo) {
        return ResponseEntity.ok(videojuegoService.buscarPorTitulo(titulo));
    }

    /**
     * Filtra videojuegos por género, plataforma y/o desarrollador
     * @param genero ID del género (opcional)
     * @param plataforma plataforma (opcional)
     * @param desarrollador ID del desarrollador (opcional)
     * @return lista de videojuegos filtrados
     */
    @GetMapping("/filtrar")
    public ResponseEntity<List<VideojuegoDTO>> filtrar(
            @RequestParam(required = false) String genero,
            @RequestParam(required = false) String plataforma,
            @RequestParam(required = false) String desarrollador) {
        return ResponseEntity.ok(videojuegoService.filtrar(genero, plataforma, desarrollador));
    }

    /**
     * Crea un nuevo videojuego
     * @param videojuegoDTO datos del nuevo videojuego
     * @return el videojuego creado
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VideojuegoDTO> crear(@Valid @RequestBody VideojuegoDTO videojuegoDTO) {
        return ResponseEntity.ok(videojuegoService.crear(videojuegoDTO));
    }

    /**
     * Actualiza un videojuego existente
     * @param id ID del videojuego
     * @param videojuegoDTO datos actualizados
     * @return el videojuego actualizado
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VideojuegoDTO> actualizar(@PathVariable String id, @Valid @RequestBody VideojuegoDTO videojuegoDTO) {
        return ResponseEntity.ok(videojuegoService.actualizar(id, videojuegoDTO));
    }

    /**
     * Elimina un videojuego
     * @param id ID del videojuego
     * @return respuesta vacía
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        videojuegoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}