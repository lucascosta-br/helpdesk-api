package com.lucascostabr.file.importer.factory;

import com.lucascostabr.exception.BadRequestException;
import com.lucascostabr.file.importer.contract.ImportadorArquivoTecnico;
import com.lucascostabr.file.importer.impl.CsvImporterTecnico;
import com.lucascostabr.file.importer.impl.XlsxImporterTecnico;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImportadorArquivoTecnicoFactory {

    private static final Logger logger = LoggerFactory.getLogger(ImportadorArquivoTecnicoFactory.class);

    @Autowired
    private ApplicationContext context;

    public ImportadorArquivoTecnico buscarImportador(String nome) throws Exception {
        if (nome == null) {
            throw new BadRequestException("Nome do arquivo não pode ser nulo");
        }

        if (nome.endsWith(".xlsx")) {
            return context.getBean(XlsxImporterTecnico.class);
        } else if (nome.endsWith(".csv")) {
            return context.getBean(CsvImporterTecnico.class);
        } else {
            throw new BadRequestException("Formato do arquivo inválido!");
        }
    }

}
