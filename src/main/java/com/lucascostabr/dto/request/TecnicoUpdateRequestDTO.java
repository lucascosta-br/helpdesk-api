package com.lucascostabr.dto.request;

import com.lucascostabr.enums.Categoria;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TecnicoUpdateRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        String nome,
        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "E-mail inválido")
        String email,
        @NotBlank(message = "A senha é obrigatória")
        String senha,
        @NotNull(message = "O setor é obrigatório")
        Categoria setor
) {
}
