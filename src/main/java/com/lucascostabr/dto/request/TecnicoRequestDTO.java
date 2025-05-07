package com.lucascostabr.dto.request;

import com.lucascostabr.enums.Categoria;

public record TecnicoRequestDTO(
        String nome,
        String email,
        String cpf,
        String senha,
        Categoria setor
) {
}
