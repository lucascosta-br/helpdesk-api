package com.lucascostabr.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    private String setor;

    @OneToMany(mappedBy = "tecnico")
    private Set<Chamado> chamados = new HashSet<>();
}
