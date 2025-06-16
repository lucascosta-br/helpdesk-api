package com.lucascostabr.file.importer.impl;

import com.lucascostabr.dto.request.TecnicoRequestDTO;
import com.lucascostabr.enums.Categoria;
import com.lucascostabr.exception.BadRequestException;
import com.lucascostabr.file.importer.contract.ImportadorArquivoTecnico;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Component
public class XlsxImporterTecnico implements ImportadorArquivoTecnico {

    @Override
    public List<TecnicoRequestDTO> importarArquivo(InputStream inputStream) throws Exception {
        try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            return parseRowsToTecnicoDTOList(rowIterator);
        }
    }

    private List<TecnicoRequestDTO> parseRowsToTecnicoDTOList(Iterator<Row> rowIterator) {
        List<TecnicoRequestDTO> tecnicos = new ArrayList<>();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (isRowValid(row)) {
                tecnicos.add(parseRowTecnicoDTO(row));
            }
        }
        return tecnicos;
    }

    private TecnicoRequestDTO parseRowTecnicoDTO(Row row) {
        String nome = getCellStringValue(row.getCell(0));
        String email = getCellStringValue(row.getCell(1));
        String cpf = getCellStringValue(row.getCell(2));
        String senha = getCellStringValue(row.getCell(3));
        String setorStr = getCellStringValue(row.getCell(4));

        if (nome == null || email == null || cpf == null || senha == null || setorStr == null) {
            throw new BadRequestException("Existem campos obrigatórios vazios na linha.");
        }

        try {
            Categoria setor = Categoria.valueOf(setorStr.trim().toUpperCase());
            return new TecnicoRequestDTO(nome, email, cpf, senha, setor);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Setor inválido: " + setorStr +
                    ". Valores válidos: " + Arrays.toString(Categoria.values()));
        }
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