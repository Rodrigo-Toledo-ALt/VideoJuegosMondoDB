package psp.videojuegosmondodb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * Configuración de MongoDB
 */
@Configuration
public class MongoConfig {

    /**
     * Configura un listener para validar los documentos antes de guardarlos
     * @param validator validador de beans
     * @return listener para validación de documentos
     */
    @Bean
    public ValidatingMongoEventListener validatingMongoEventListener(LocalValidatorFactoryBean validator) {
        return new ValidatingMongoEventListener(validator);
    }

    /**
     * Configura el validador de beans
     * @return validador de beans
     */
    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }
}