package psp.videojuegosmondodb.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import psp.videojuegosmondodb.dto.GeneroDTO;
import psp.videojuegosmondodb.service.GeneroService;

import java.util.List;

/**
 * Controlador para operaciones con géneros
 */
@RestController
@RequestMapping("/generos")
public class GeneroController {

    private final GeneroService generoService;

    public GeneroController(GeneroService generoService) {
        this.generoService = generoService;
    }

    /**
     * Obtiene todos los géneros
     * @return lista de géneros
     */
    @GetMapping
    public ResponseEntity<List<GeneroDTO>> obtenerTodos() {
        return ResponseEntity.ok(generoService.obtenerTodos());
    }

    /**
     * Obtiene un género por su ID
     * @param id ID del género
     * @return el género
     */
    @GetMapping("/{id}")
    public ResponseEntity<GeneroDTO> obtenerPorId(@PathVariable String id) {
        return ResponseEntity.ok(generoService.obtenerPorId(id));
    }

    /**
     * Crea un nuevo género
     * @param generoDTO datos del nuevo género
     * @return el género creado
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GeneroDTO> crear(@Valid @RequestBody GeneroDTO generoDTO) {
        return ResponseEntity.ok(generoService.crear(generoDTO));
    }

    /**
     * Actualiza un género existente
     * @param id ID del género
     * @param generoDTO datos actualizados
     * @return el género actualizado
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GeneroDTO> actualizar(@PathVariable String id, @Valid @RequestBody GeneroDTO generoDTO) {
        return ResponseEntity.ok(generoService.actualizar(id, generoDTO));
    }

    /**
     * Elimina un género
     * @param id ID del género
     * @return respuesta vacía
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        generoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}