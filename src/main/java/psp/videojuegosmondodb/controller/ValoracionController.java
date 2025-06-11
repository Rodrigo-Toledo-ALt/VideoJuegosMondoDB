package psp.videojuegosmondodb.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import psp.videojuegosmondodb.dto.ValoracionDTO;
import psp.videojuegosmondodb.model.Usuario;
import psp.videojuegosmondodb.service.ValoracionService;

import java.util.List;

/**
 * Controlador para operaciones con valoraciones
 */
@RestController
@RequestMapping("/valoraciones")
public class ValoracionController {

    private final ValoracionService valoracionService;

    public ValoracionController(ValoracionService valoracionService) {
        this.valoracionService = valoracionService;
    }

    /**
     * Obtiene todas las valoraciones de un videojuego
     * @param id ID del videojuego
     * @return lista de valoraciones
     */
    @GetMapping("/videojuego/{id}")
    public ResponseEntity<List<ValoracionDTO>> obtenerPorVideojuego(@PathVariable String id) {
        return ResponseEntity.ok(valoracionService.obtenerPorVideojuego(id));
    }

    /**
     * Crea una nueva valoración
     * @param valoracionDTO datos de la nueva valoración
     * @param authentication datos de autenticación del usuario
     * @return la valoración creada
     */
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ValoracionDTO> crear(@Valid @RequestBody ValoracionDTO valoracionDTO, Authentication authentication) {
        Usuario usuario = (Usuario) authentication.getPrincipal();
        return ResponseEntity.ok(valoracionService.crear(valoracionDTO, usuario.getId()));
    }
}