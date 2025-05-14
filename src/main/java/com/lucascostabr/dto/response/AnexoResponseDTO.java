package com.lucascostabr.dto.response;

import java.io.Serializable;

public record AnexoResponseDTO(
        Long id,
        String nome,
        String tipo,
        String caminho,
        long tamanho
) implements Serializable {
    private static final long serialVersionUID = 1L;
}
