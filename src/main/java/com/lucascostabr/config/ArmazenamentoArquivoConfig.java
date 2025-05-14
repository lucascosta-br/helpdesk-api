package com.lucascostabr.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "file")
@Data
public class ArmazenamentoArquivoConfig {

    private String uploadDir;
}
