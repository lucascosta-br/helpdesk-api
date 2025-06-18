package com.lucascostabr.file.exporter.impl;

import com.lucascostabr.dto.relatorio.RelatorioGeralDTO;
import com.lucascostabr.exception.FileNotFoundException;
import com.lucascostabr.file.exporter.contract.ExportadorArquivoChamado;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PdfExporterChamado implements ExportadorArquivoChamado {

    @Override
    public Resource exportadorArquivo(List<RelatorioGeralDTO> relatorios) throws Exception {
        InputStream inputStream = getClass().getResourceAsStream("/templates/relatorio-chamado-geral.jrxml");
        if (inputStream == null) {
            throw new FileNotFoundException("Template não encontrado: /templates/relatorio-chamado-geral.jrxml");
        }

        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(relatorios);
        Map<String, Object> parameters = new HashMap<>();

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            return new ByteArrayResource(outputStream.toByteArray());
        }
    }
}
