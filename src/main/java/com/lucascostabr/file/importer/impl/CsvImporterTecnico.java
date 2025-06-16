package com.lucascostabr.file.importer.impl;

import com.lucascostabr.dto.request.TecnicoRequestDTO;
import com.lucascostabr.enums.Categoria;
import com.lucascostabr.exception.BadRequestException;
import com.lucascostabr.file.importer.contract.ImportadorArquivoTecnico;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CsvImporterTecnico implements ImportadorArquivoTecnico {

    @Override
    public List<TecnicoRequestDTO> importarArquivo(InputStream inputStream) throws Exception {
        CSVFormat format = CSVFormat.Builder.create()
                .setHeader("nome", "email", "cpf", "senha", "setor")
                .setSkipHeaderRecord(true)
                .setIgnoreEmptyLines(true)
                .setTrim(true)
                .get();

        Iterable<CSVRecord> records = format.parse(new InputStreamReader(inputStream));
        return converterRecordsParaDTO(records);
    }

    private List<TecnicoRequestDTO> converterRecordsParaDTO(Iterable<CSVRecord> records) {
        List<TecnicoRequestDTO> tecnicos = new ArrayList<>();
        int linha = 1; // Começa em 1 porque pulamos o cabeçalho

        for (CSVRecord record : records) {
            linha++;
            try {
                String setorStr = record.get("setor");
                Categoria setor;
                try {
                    setor = Categoria.valueOf(setorStr.toUpperCase());
                } catch (IllegalArgumentException e) {
                    throw new BadRequestException("Linha " + linha + ": Valor inválido para setor: '" +
                            setorStr + "'. Valores válidos: " + Arrays.toString(Categoria.values()));
                }

                TecnicoRequestDTO dto = new TecnicoRequestDTO(
                        record.get("nome"),
                        record.get("email"),
                        record.get("cpf"),
                        record.get("senha"),
                        setor
                );
                tecnicos.add(dto);
            } catch (Exception e) {
                throw new BadRequestException("Erro na linha " + linha + ": " + e.getMessage());
            }
        }

        if (tecnicos.isEmpty()) {
            throw new BadRequestException("O arquivo não contém dados válidos para importação");
        }

        return tecnicos;
    }
}
