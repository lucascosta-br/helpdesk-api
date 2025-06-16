package com.lucascostabr.file.exporter.impl;

import com.lucascostabr.dto.response.TecnicoResponseDTO;
import com.lucascostabr.file.exporter.contract.ExportadorArquivoTecnico;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Component
public class XlsxExporterTecnico implements ExportadorArquivoTecnico {

    @Override
    public Resource exportadorArquivo(List<TecnicoResponseDTO> tecnicos) throws Exception {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Tecnicos");

            Row headerRow = sheet.createRow(0);
            String[] headers = {"id", "nome", "email", "cpf", "setor", "perfil"};

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(createHeaderCellStyle(workbook));
            }

            int rowIndex = 1;
            for (TecnicoResponseDTO dto : tecnicos) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(dto.getId());
                row.createCell(1).setCellValue(dto.getNome());
                row.createCell(2).setCellValue(dto.getEmail());
                row.createCell(3).setCellValue(dto.getCpf());
                row.createCell(4).setCellValue(dto.getSetor().name());
                row.createCell(5).setCellValue(dto.getPerfil().name());
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return new ByteArrayResource(outputStream.toByteArray());
        }
    }

    private CellStyle createHeaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

}