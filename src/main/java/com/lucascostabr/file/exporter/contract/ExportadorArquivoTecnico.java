package com.lucascostabr.file.exporter.contract;

import com.lucascostabr.dto.response.TecnicoResponseDTO;
import org.springframework.core.io.Resource;

import java.util.List;

public interface ExportadorArquivoTecnico {
    Resource exportadorArquivo(List<TecnicoResponseDTO> tecnicos) throws Exception;
}
