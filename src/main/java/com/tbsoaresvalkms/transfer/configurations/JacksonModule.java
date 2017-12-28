package com.tbsoaresvalkms.transfer.configurations;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonModule {
    @Bean
    public Module module() {
        return new JavaTimeModule();
    }
}
