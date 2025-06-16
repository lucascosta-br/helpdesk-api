package com.lucascostabr.file.importer.impl;

import com.lucascostabr.dto.request.ClienteRequestDTO;
import com.lucascostabr.file.importer.contract.ImportadorArquivoCliente;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class XlsxImporterCliente implements ImportadorArquivoCliente {

    @Override
    public List<ClienteRequestDTO> importarArquivo(InputStream inputStream) throws Exception {
        try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            return parseRowsToClienteDTOList(rowIterator);
        }
    }

    private List<ClienteRequestDTO> parseRowsToClienteDTOList(Iterator<Row> rowIterator) {
        List<ClienteRequestDTO> clientes = new ArrayList<>();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (isRowValid(row)) {
                clientes.add(parseRowClienteDTO(row));
            }
        }
        return clientes;
    }

    private ClienteRequestDTO parseRowClienteDTO(Row row) {
        ClienteRequestDTO dto = new ClienteRequestDTO(
                row.getCell(0).getStringCellValue(),
                row.getCell(1).getStringCellValue(),
                row.getCell(2).getStringCellValue(),
                row.getCell(3).getStringCellValue(),
                row.getCell(4).getStringCellValue()
        );
        return dto;
    }

    private static boolean isRowValid(Row row) {
        return row.getCell(0) != null && row.getCell(0).getCellType() != CellType.BLANK;
    }
}