package psp.videojuegosmondodb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.lang.NonNull;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.Year;
import java.util.Arrays;

/**
 * Configuración de MongoDB con convertidores personalizados
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

    /**
     * Registra los convertidores personalizados para MongoDB
     * @return configuración de conversiones personalizadas
     */
    @Bean
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(Arrays.asList(
                new IntegerToYearConverter(),
                new YearToIntegerConverter()
        ));
    }

    /**
     * Convertidor para leer Integer desde MongoDB y convertirlo a Year
     */
    @ReadingConverter
    public static class IntegerToYearConverter implements Converter<Integer, Year> {

        @Override
        public Year convert(@NonNull Integer source) {
            return Year.of(source);
        }
    }

    /**
     * Convertidor para escribir Year a MongoDB como Integer
     */
    @WritingConverter
    public static class YearToIntegerConverter implements Converter<Year, Integer> {

        @Override
        public Integer convert(@NonNull Year source) {
            return source.getValue();
        }
    }
}