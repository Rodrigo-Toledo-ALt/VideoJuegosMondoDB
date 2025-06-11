package psp.videojuegosmondodb.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import psp.videojuegosmondodb.dto.DesarrolladorDTO;
import psp.videojuegosmondodb.service.DesarrolladorService;

import java.util.List;

/**
 * Controlador para operaciones con desarrolladores
 */
@RestController
@RequestMapping("/desarrolladores")
public class DesarrolladorController {

    private final DesarrolladorService desarrolladorService;

    public DesarrolladorController(DesarrolladorService desarrolladorService) {
        this.desarrolladorService = desarrolladorService;
    }

    /**
     * Obtiene todos los desarrolladores
     * @return lista de desarrolladores
     */
    @GetMapping
    public ResponseEntity<List<DesarrolladorDTO>> obtenerTodos() {
        return ResponseEntity.ok(desarrolladorService.obtenerTodos());
    }

    /**
     * Obtiene un desarrollador por su ID
     * @param id ID del desarrollador
     * @return el desarrollador
     */
    @GetMapping("/{id}")
    public ResponseEntity<DesarrolladorDTO> obtenerPorId(@PathVariable String id) {
        return ResponseEntity.ok(desarrolladorService.obtenerPorId(id));
    }

    /**
     * Crea un nuevo desarrollador
     * @param desarrolladorDTO datos del nuevo desarrollador
     * @return el desarrollador creado
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DesarrolladorDTO> crear(@Valid @RequestBody DesarrolladorDTO desarrolladorDTO) {
        return ResponseEntity.ok(desarrolladorService.crear(desarrolladorDTO));
    }

    /**
     * Actualiza un desarrollador existente
     * @param id ID del desarrollador
     * @param desarrolladorDTO datos actualizados
     * @return el desarrollador actualizado
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DesarrolladorDTO> actualizar(@PathVariable String id, @Valid @RequestBody DesarrolladorDTO desarrolladorDTO) {
        return ResponseEntity.ok(desarrolladorService.actualizar(id, desarrolladorDTO));
    }

    /**
     * Elimina un desarrollador
     * @param id ID del desarrollador
     * @return respuesta vacía
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        desarrolladorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}