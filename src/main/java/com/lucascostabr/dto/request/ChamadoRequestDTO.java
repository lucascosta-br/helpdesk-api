package com.lucascostabr.dto.request;

import com.lucascostabr.enums.Categoria;
import com.lucascostabr.enums.Prioridade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ChamadoRequestDTO(
        @NotBlank(message = "O título é obrigatório")
        String titulo,
        @NotBlank(message = "A descrição é obrigatória")
        String descricao,
        @NotNull(message = "A prioridade é obrigatória")
        Prioridade prioridade,
        @NotNull(message = "A categoria é obrigatória")
        Categoria categoria,
        @NotNull(message = "O ID do cliente é obrigatório")
        Long clienteId
) {
}