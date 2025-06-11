package psp.videojuegosmondodb.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import psp.videojuegosmondodb.dto.RegistroRequest;
import psp.videojuegosmondodb.dto.UsuarioDTO;
import psp.videojuegosmondodb.exception.DuplicateResourceException;
import psp.videojuegosmondodb.exception.ResourceNotFoundException;
import psp.videojuegosmondodb.model.Rol;
import psp.videojuegosmondodb.model.Usuario;
import psp.videojuegosmondodb.repository.UsuarioRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para operaciones con usuarios
 */
@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Carga un usuario por su nombre de usuario (email)
     * @param email el email del usuario
     * @return el usuario
     */
    @Override
    public Usuario loadUserByUsername(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "email", email));
    }

    /**
     * Obtiene todos los usuarios
     * @return lista de usuarios
     */
    public List<UsuarioDTO> obtenerTodos() {
        return usuarioRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un usuario por su ID
     * @param id ID del usuario
     * @return el usuario
     */
    public UsuarioDTO obtenerPorId(String id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));
        return mapToDTO(usuario);
    }

    /**
     * Crea un nuevo usuario
     * @param dto datos del nuevo usuario
     * @return el usuario creado
     */
    public UsuarioDTO crear(UsuarioDTO dto) {
        // Verificar si ya existe un usuario con ese email
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Ya existe un usuario con el email: " + dto.getEmail());
        }
        
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(passwordEncoder.encode("password123")); // Contraseña por defecto
        usuario.setRol(dto.getRol());
        
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return mapToDTO(usuarioGuardado);
    }

    /**
     * Registra un nuevo usuario con rol USER
     * @param request datos de registro
     * @return el usuario registrado
     */
    public UsuarioDTO registrar(RegistroRequest request) {
        // Verificar si ya existe un usuario con ese email
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Ya existe un usuario con el email: " + request.getEmail());
        }
        
        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(Rol.USER);
        
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return mapToDTO(usuarioGuardado);
    }

    /**
     * Actualiza un usuario existente
     * @param id ID del usuario
     * @param dto datos actualizados
     * @return el usuario actualizado
     */
    public UsuarioDTO actualizar(String id, UsuarioDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));
        
        // Verificar si el email ya está en uso por otro usuario
        if (!usuario.getEmail().equals(dto.getEmail()) && usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Ya existe un usuario con el email: " + dto.getEmail());
        }
        
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setRol(dto.getRol());
        
        Usuario usuarioActualizado = usuarioRepository.save(usuario);
        return mapToDTO(usuarioActualizado);
    }

    /**
     * Elimina un usuario
     * @param id ID del usuario
     */
    public void eliminar(String id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario", "id", id);
        }
        usuarioRepository.deleteById(id);
    }

    /**
     * Convierte una entidad Usuario a DTO
     * @param usuario entidad Usuario
     * @return DTO de Usuario
     */
    private UsuarioDTO mapToDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setEmail(usuario.getEmail());
        dto.setRol(usuario.getRol());
        return dto;
    }
}