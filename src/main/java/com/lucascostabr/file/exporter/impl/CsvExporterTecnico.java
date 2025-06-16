package com.lucascostabr.file.exporter.impl;

import com.lucascostabr.dto.response.TecnicoResponseDTO;
import com.lucascostabr.file.exporter.contract.ExportadorArquivoTecnico;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class CsvExporterTecnico implements ExportadorArquivoTecnico {

    @Override
    public Resource exportadorArquivo(List<TecnicoResponseDTO> tecnicos) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);

        CSVFormat csvFormat = CSVFormat.Builder.create()
                .setHeader("id", "nome", "email", "cpf", "setor", "perfil")
                .setSkipHeaderRecord(false)
                .build();
                //.get();

        try (CSVPrinter csvPrinter = new CSVPrinter(writer, csvFormat)) {
            for (TecnicoResponseDTO dto : tecnicos) {
                csvPrinter.printRecord(
                        dto.getId(),
                        dto.getNome(),
                        dto.getEmail(),
                        dto.getCpf(),
                        dto.getSetor().name(),
                        dto.getPerfil().name()
                );
            }
        }
        return new ByteArrayResource(outputStream.toByteArray());
    }
}
