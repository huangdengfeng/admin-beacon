package com.seezoon.infrastructure.configuration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import java.io.IOException;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * jackson configuration
 */
@Configuration
public class JacksonCustomizer {

    /**
     * json trim when deserialize
     *
     * @return
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonTrimWhenDeserialize() {
        return jacksonObjectMapperBuilder -> {
            jacksonObjectMapperBuilder.deserializerByType(String.class,
                    new StdScalarDeserializer<String>(String.class) {

                        private static final long serialVersionUID = 1L;

                        @Override
                        public String deserialize(JsonParser jsonParser, DeserializationContext ctx)
                                throws IOException {
                            String valueAsString = jsonParser.getValueAsString();
                            if (null != valueAsString) {
                                valueAsString = StringEscapeUtils.escapeHtml4(valueAsString.trim());
                            }
                            return valueAsString;
                        }
                    });
        };
    }
}
