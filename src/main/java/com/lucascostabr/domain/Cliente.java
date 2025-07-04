package com.lucascostabr.domain;

import com.lucascostabr.enums.TipoPerfil;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clientes")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class Cliente extends Usuario{
    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private String empresa;

    @OneToMany(mappedBy = "cliente")
    private Set<Chamado> chamados = new HashSet<>();

    public Cliente(String email, String senha, TipoPerfil perfil) {
        super(email, senha, perfil);
    }

    public Cliente(String nome, String email, String cpf, String senhaEncriptada) {
        super();
    }
}
