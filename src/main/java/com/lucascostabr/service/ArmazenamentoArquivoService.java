package com.lucascostabr.service;

import com.lucascostabr.config.ArmazenamentoArquivoConfig;
import com.lucascostabr.exception.FileNotFoundException;
import com.lucascostabr.exception.FileStorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ArmazenamentoArquivoService {
    private static final Logger logger = LoggerFactory.getLogger(ArmazenamentoArquivoService.class);

    private final Path localArmazenamentoArquivo;

    public ArmazenamentoArquivoService(ArmazenamentoArquivoConfig config) {
        this.localArmazenamentoArquivo = Paths.get(config.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.localArmazenamentoArquivo);
        } catch (Exception e) {
            throw new FileStorageException("Erro ao criar diretório de upload", e);
        }
    }

    public String armazenarArquivo(MultipartFile file) {
        logger.info("Armazenando um Arquivo!");

        String nomeArquivo = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (nomeArquivo.contains("..")) {
                throw new FileStorageException("Nome de armazenarArquivo inválido: " + nomeArquivo);
            }

            Path localDestino = this.localArmazenamentoArquivo.resolve(nomeArquivo);
            Files.copy(file.getInputStream(), localDestino, StandardCopyOption.REPLACE_EXISTING);
            return nomeArquivo;
        } catch (Exception e) {
            throw new FileStorageException("Erro ao salvar o arquivo: " + nomeArquivo, e);
        }
    }

    public Resource carregarArquivo(String nomeArquivo) {
        logger.info("Carregando um Arquivo!");

        try {
            Path caminhoArquivo = this.localArmazenamentoArquivo.resolve(nomeArquivo).normalize();
            Resource resource = new UrlResource(caminhoArquivo.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("Arquivo não encontrado: " + nomeArquivo);
            }
        } catch (Exception e) {
            throw new FileNotFoundException("Arquivo não encontrado: " + nomeArquivo, e);
        }
    }

}
