package com.lucascostabr.domain;

import com.lucascostabr.enums.Categoria;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Tecnico extends Usuario{
    private static final long serialVersionUID = 1L;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Categoria setor;

    @OneToMany(mappedBy = "tecnico")
    private Set<Chamado> chamados = new HashSet<>();
}
