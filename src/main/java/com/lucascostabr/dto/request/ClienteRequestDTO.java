package com.lucascostabr.dto.request;

public record ClienteRequestDTO(
        String nome,
        String email,
        String cpf,
        String senha,
        String empresa
) {
}
