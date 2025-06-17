package com.lucascostabr.file.exporter.factory;

import com.lucascostabr.exception.BadRequestException;
import com.lucascostabr.file.exporter.MediaTypes;
import com.lucascostabr.file.exporter.contract.ExportadorArquivoCliente;
import com.lucascostabr.file.exporter.impl.CsvExporterCliente;
import com.lucascostabr.file.exporter.impl.XlsxExporterCliente;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExportadorArquivoClienteFactory {

    private static final Logger logger = LoggerFactory.getLogger(ExportadorArquivoClienteFactory.class);

    @Autowired
    private ApplicationContext context;

    public ExportadorArquivoCliente buscarExportador(String acceptHeader) throws Exception {
        if (acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_XLSX_VALUE)) {
            return context.getBean(XlsxExporterCliente.class);
        } else if (acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_CSV_VALUE)) {
            return context.getBean(CsvExporterCliente.class);
        } else {
            throw new BadRequestException("Formato do arquivo inválido!");
        }
    }

}
