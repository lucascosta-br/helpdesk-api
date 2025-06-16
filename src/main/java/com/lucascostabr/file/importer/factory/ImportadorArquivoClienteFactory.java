package com.lucascostabr.file.importer.factory;

import com.lucascostabr.exception.BadRequestException;
import com.lucascostabr.file.importer.contract.ImportadorArquivoCliente;
import com.lucascostabr.file.importer.impl.CsvImporterCliente;
import com.lucascostabr.file.importer.impl.XlsxImporterCliente;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImportadorArquivoClienteFactory {

    private static final Logger logger = LoggerFactory.getLogger(ImportadorArquivoClienteFactory.class);

    @Autowired
    private ApplicationContext context;

    public ImportadorArquivoCliente buscarImportador(String nome) throws Exception {
        if (nome == null) {
            throw new BadRequestException("Nome do arquivo não pode ser nulo");
        }

        if (nome.endsWith(".xlsx")) {
            return context.getBean(XlsxImporterCliente.class);
        } else if (nome.endsWith(".csv")) {
            return context.getBean(CsvImporterCliente.class);
        } else {
            throw new BadRequestException("Formato do arquivo inválido!");
        }
    }

}
