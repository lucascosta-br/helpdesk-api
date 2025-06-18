package com.lucascostabr.dto.relatorio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelatorioGeralDTO {
    private Long id;
    private String status;
    private String prioridade;
    private String categoria;
    private Timestamp dataAbertura;
    private Timestamp dataFechamento;
}
