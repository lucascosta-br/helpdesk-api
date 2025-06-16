package com.lucascostabr.file.importer.impl;

import com.lucascostabr.dto.request.ClienteRequestDTO;
import com.lucascostabr.exception.BadRequestException;
import com.lucascostabr.file.importer.contract.ImportadorArquivoCliente;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvImporterCliente implements ImportadorArquivoCliente {

    @Override
    public List<ClienteRequestDTO> importarArquivo(InputStream inputStream) throws Exception {
        CSVFormat format = CSVFormat.Builder.create()
                .setHeader("nome", "email", "cpf", "senha", "empresa")
                .setSkipHeaderRecord(true)
                .setIgnoreEmptyLines(true)
                .setTrim(true)
                .get();

        Iterable<CSVRecord> records = format.parse(new InputStreamReader(inputStream));
        return converterRecordsParaDTO(records);
    }

    private List<ClienteRequestDTO> converterRecordsParaDTO(Iterable<CSVRecord> records) {
        List<ClienteRequestDTO> clientes = new ArrayList<>();

        for (CSVRecord record : records) {
            ClienteRequestDTO dto = new ClienteRequestDTO(
                    record.get("nome"),
                    record.get("email"),
                    record.get("cpf"),
                    record.get("senha"),
                    record.get("empresa")
            );
            clientes.add(dto);
        }

        if (clientes.isEmpty()) {
            throw new BadRequestException("O arquivo não contém dados válidos para importação");
        }

        return clientes;
    }
}
