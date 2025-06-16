package com.lucascostabr.file.importer.contract;

import com.lucascostabr.dto.request.ClienteRequestDTO;

import java.io.InputStream;
import java.util.List;

public interface ImportadorArquivoCliente {
    List<ClienteRequestDTO> importarArquivo(InputStream inputStream) throws Exception;
}
