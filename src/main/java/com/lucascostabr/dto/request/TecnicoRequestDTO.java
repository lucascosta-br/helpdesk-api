package com.lucascostabr.dto.request;

import com.lucascostabr.enums.Categoria;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

public record TecnicoRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        String nome,
        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "E-mail inválido")
        String email,
        @NotBlank(message = "O CPF é obrigatório")
        @CPF(message = "CPF inválido")
        String cpf,
        @NotBlank(message = "A senha é obrigatória")
        String senha,
        @NotNull(message = "O setor é obrigatório")
        Categoria setor
) {
}
