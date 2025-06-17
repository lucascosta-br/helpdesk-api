package com.lucascostabr.file.exporter.contract;

import com.lucascostabr.dto.response.ClienteResponseDTO;
import org.springframework.core.io.Resource;

import java.util.List;

public interface ExportadorArquivoCliente {
    Resource exportadorArquivo(List<ClienteResponseDTO> clientes) throws Exception;
}
