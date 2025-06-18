package com.lucascostabr.service;

import com.lucascostabr.dto.relatorio.RelatorioGeralDTO;
import com.lucascostabr.file.exporter.factory.ExportadorArquivoChamadoFactory;
import com.lucascostabr.mapper.RelatorioGeralMapper;
import com.lucascostabr.repository.ChamadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RelatorioChamadoService {

    private final ChamadoRepository chamadoRepository;
    private final RelatorioGeralMapper mapper;
    private final ExportadorArquivoChamadoFactory exportadorFactory;

    public Resource gerarRelatorioGeral(String acceptHeader) throws Exception {
        List<RelatorioGeralDTO> chamados = chamadoRepository.findAll().stream()
                .map(mapper::toRelatorioGeralDTO)
                .toList();

        var exportador = exportadorFactory.buscarExportador(acceptHeader);
        return exportador.exportadorArquivo(chamados);
    }
}

