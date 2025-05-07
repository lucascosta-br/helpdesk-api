package com.lucascostabr.dto.response;

import com.lucascostabr.enums.Categoria;
import com.lucascostabr.enums.Prioridade;
import com.lucascostabr.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

public record ChamadoResponseDTO(
        Long id,
        String titulo,
        String descricao,
        LocalDateTime dataAbertura,
        LocalDateTime dataFechamento,
        Status status,
        Prioridade prioridade,
        Categoria categoria,
        ClienteResponseDTO cliente,
        TecnicoResponseDTO tecnico,
        List<AnexoResponseDTO> anexos
) {
}
