package com.lucascostabr.domain;

import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Cliente extends Usuario{
    private static final long serialVersionUID = 1L;

    private String empresa;

    @OneToMany(mappedBy = "cliente")
    private Set<Chamado> chamados = new HashSet<>();
}
