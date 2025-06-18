package com.lucascostabr.controller;

import com.lucascostabr.file.exporter.MediaTypes;
import com.lucascostabr.service.RelatorioChamadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/relatorios")
@RequiredArgsConstructor
public class RelatorioChamadoController {

    private final RelatorioChamadoService relatorioChamadoService;

    @GetMapping("/chamados")
    public ResponseEntity<Resource> gerarRelatorio(
            @RequestHeader(name = "Accept") String acceptHeader) throws Exception {

        Resource arquivo = relatorioChamadoService.gerarRelatorioGeral(acceptHeader);

        String extensao = switch (acceptHeader) {
            case MediaTypes.APPLICATION_PDF_VALUE -> "pdf";
            case MediaTypes.APPLICATION_CSV_VALUE -> "csv";
            case MediaTypes.APPLICATION_XLSX_VALUE -> "xlsx";
            default -> throw new IllegalArgumentException("Formato não suportado: " + acceptHeader);
        };

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(acceptHeader))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=relatorio-chamados." + extensao)
                .body(arquivo);
    }
}


