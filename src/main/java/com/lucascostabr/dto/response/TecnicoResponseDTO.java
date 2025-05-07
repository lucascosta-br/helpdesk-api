package com.lucascostabr.dto.response;

import com.lucascostabr.enums.Categoria;
import com.lucascostabr.enums.TipoPerfil;

public record TecnicoResponseDTO(
        Long id,
        String nome,
        String email,
        String cpf,
        Categoria setor,
        TipoPerfil perfil
) {
}
