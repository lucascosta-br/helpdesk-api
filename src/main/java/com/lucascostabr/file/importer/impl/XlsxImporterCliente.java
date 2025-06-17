package com.lucascostabr.file.importer.impl;

import com.lucascostabr.dto.request.ClienteRequestDTO;
import com.lucascostabr.file.importer.contract.ImportadorArquivoCliente;
import org.apache.poi.ss.usermodel.Cell;
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
        if (row == null) {
            throw new RuntimeException("Linha nula encontrada no arquivo");
        }

        ClienteRequestDTO dto = new ClienteRequestDTO(
                getCellStringValue(row.getCell(0)),
                getCellStringValue(row.getCell(1)),
                getCellStringValue(row.getCell(2)),
                getCellStringValue(row.getCell(3)),
                getCellStringValue(row.getCell(4))
        );
        return dto;
    }

    private String getCellStringValue(Cell cell) {
        if (cell == null) return null;

        try {
            return switch (cell.getCellType()) {
                case STRING -> cell.getStringCellValue().trim();
                case NUMERIC -> {
                    double numValue = cell.getNumericCellValue();
                    // Verifica se é inteiro
                    if (numValue == Math.floor(numValue)) {
                        yield String.valueOf((long) numValue);
                    }
                    yield String.valueOf(numValue);
                }
                case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
                case FORMULA -> switch (cell.getCachedFormulaResultType()) {
                    case STRING -> cell.getStringCellValue();
                    case NUMERIC -> String.valueOf(cell.getNumericCellValue());
                    case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
                    default -> null;
                };
                case BLANK -> null;
                default -> null;
            };
        } catch (Exception e) {
            throw new RuntimeException("Erro ao ler valor da célula: " + e.getMessage());
        }
    }

    private static boolean isRowValid(Row row) {
        return row.getCell(0) != null && row.getCell(0).getCellType() != CellType.BLANK;
    }
}