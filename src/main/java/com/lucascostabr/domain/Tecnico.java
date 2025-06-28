package com.lucascostabr.domain;

import com.lucascostabr.enums.Categoria;
import com.lucascostabr.enums.TipoPerfil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tecnicos")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class Tecnico extends Usuario{
    private static final long serialVersionUID = 1L;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Categoria setor;

    @OneToMany(mappedBy = "tecnico")
    private Set<Chamado> chamados = new HashSet<>();

    public Tecnico(String email, String senha, TipoPerfil perfil) {
        super(email, senha, perfil);
    }

    public Tecnico(String nome, String email, String cpf, String senhaEncriptada) {
        super();
    }
}
