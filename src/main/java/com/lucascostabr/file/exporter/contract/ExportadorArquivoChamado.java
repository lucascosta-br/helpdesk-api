package com.lucascostabr.file.exporter.contract;

import com.lucascostabr.dto.relatorio.RelatorioGeralDTO;
import org.springframework.core.io.Resource;

import java.util.List;

public interface ExportadorArquivoChamado {
    Resource exportadorArquivo(List<RelatorioGeralDTO> relatorios) throws Exception;
}
