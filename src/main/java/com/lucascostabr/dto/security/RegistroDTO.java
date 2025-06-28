package com.lucascostabr.dto.security;

import com.lucascostabr.enums.TipoPerfil;

public record RegistroDTO(String nome,String email, String senha, String cpf, TipoPerfil perfil) {
}
