package psp.videojuegosmondodb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * Configuración de seguridad de Spring
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    public SecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // ✅ Endpoints públicos
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/videojuegos/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/generos/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/desarrolladores/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/valoraciones/**").permitAll() // 🆕 GET valoraciones público

                        // ✅ Valoraciones - POST requiere autenticación (cualquier usuario)
                        .requestMatchers(HttpMethod.POST, "/valoraciones").authenticated()

                        // ✅ Endpoints específicos para ADMIN (videojuegos)
                        .requestMatchers(HttpMethod.POST, "/videojuegos").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/videojuegos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/videojuegos/**").hasRole("ADMIN")

                        // ✅ Endpoints específicos para ADMIN (géneros)
                        .requestMatchers(HttpMethod.POST, "/generos").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/generos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/generos/**").hasRole("ADMIN")

                        // ✅ Endpoints específicos para ADMIN (desarrolladores)
                        .requestMatchers(HttpMethod.POST, "/desarrolladores").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/desarrolladores/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/desarrolladores/**").hasRole("ADMIN")

                        // ✅ Endpoints de usuarios (solo ADMIN)
                        .requestMatchers("/usuarios/**").hasRole("ADMIN")

                        // El resto de endpoints requieren autenticación
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));
        configuration.setExposedHeaders(List.of("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}