package com.lucascostabr.file.exporter.factory;

import com.lucascostabr.exception.BadRequestException;
import com.lucascostabr.file.exporter.MediaTypes;
import com.lucascostabr.file.exporter.contract.ExportadorArquivoTecnico;
import com.lucascostabr.file.exporter.impl.CsvExporterTecnico;
import com.lucascostabr.file.exporter.impl.XlsxExporterTecnico;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExportadorArquivoTecnicoFactory {

    private static final Logger logger = LoggerFactory.getLogger(ExportadorArquivoTecnicoFactory.class);

    @Autowired
    private ApplicationContext context;

    public ExportadorArquivoTecnico buscarExportador(String acceptHeader) throws Exception {
        if (acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_XLSX_VALUE)) {
            return context.getBean(XlsxExporterTecnico.class);
        } else if (acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_CSV_VALUE)) {
            return context.getBean(CsvExporterTecnico.class);
        } else {
            throw new BadRequestException("Formato do arquivo inválido!");
        }
    }

}
