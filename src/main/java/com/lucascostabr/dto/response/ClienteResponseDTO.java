package com.lucascostabr.dto.response;

import com.lucascostabr.enums.TipoPerfil;

public record ClienteResponseDTO(
        Long id,
        String nome,
        String email,
        String cpf,
        String empresa,
        TipoPerfil perfil
) {
}
