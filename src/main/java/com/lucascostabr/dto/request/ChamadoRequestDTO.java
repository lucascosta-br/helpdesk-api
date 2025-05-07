package com.lucascostabr.dto.request;

import com.lucascostabr.enums.Categoria;
import com.lucascostabr.enums.Prioridade;
import com.lucascostabr.enums.Status;

public record ChamadoRequestDTO(
        String titulo,
        String descricao,
        Status status,
        Prioridade prioridade,
        Categoria categoria,
        Long clienteId,
        Long tecnicoId
) {
}