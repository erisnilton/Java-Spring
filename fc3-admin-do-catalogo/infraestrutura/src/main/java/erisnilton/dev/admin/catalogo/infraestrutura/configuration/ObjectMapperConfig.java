package erisnilton.dev.admin.catalogo.infraestrutura.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import erisnilton.dev.admin.catalogo.infraestrutura.configuration.json.Json;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return Json.mapper();
    }


}
