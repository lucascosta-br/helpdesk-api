package com.lucascostabr.file.importer.contract;

import com.lucascostabr.dto.request.TecnicoRequestDTO;

import java.io.InputStream;
import java.util.List;

public interface ImportadorArquivoTecnico {
    List<TecnicoRequestDTO> importarArquivo(InputStream inputStream) throws Exception;
}
