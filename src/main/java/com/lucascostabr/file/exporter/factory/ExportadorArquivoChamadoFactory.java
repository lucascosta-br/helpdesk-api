package com.lucascostabr.file.exporter.factory;

import com.lucascostabr.exception.BadRequestException;
import com.lucascostabr.file.exporter.MediaTypes;
import com.lucascostabr.file.exporter.contract.ExportadorArquivoChamado;
import com.lucascostabr.file.exporter.impl.PdfExporterChamado;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExportadorArquivoChamadoFactory {

    private static final Logger logger = LoggerFactory.getLogger(ExportadorArquivoChamadoFactory.class);

    @Autowired
    private ApplicationContext context;

    public ExportadorArquivoChamado buscarExportador(String acceptHeader) throws Exception {
        if (acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_PDF_VALUE)) {
            return context.getBean(PdfExporterChamado.class);
        } else {
            throw new BadRequestException("Formato do arquivo inválido!");
        }
    }
}
