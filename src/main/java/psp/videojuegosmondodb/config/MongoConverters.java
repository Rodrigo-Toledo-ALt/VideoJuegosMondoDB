package psp.videojuegosmondodb.config;


import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.Year;

/**
 * Convertidores personalizados para MongoDB
 */
public class MongoConverters {

    /**
     * Convertidor para leer Integer desde MongoDB y convertirlo a Year
     */
    @Component
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
    @Component
    @WritingConverter
    public static class YearToIntegerConverter implements Converter<Year, Integer> {

        @Override
        public Integer convert(@NonNull Year source) {
            return source.getValue();
        }
    }
}