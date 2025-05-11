package com.lucascostabr.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lucascostabr.enums.TipoPerfil;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true ,length = 100)
    @Email
    private String email;

    @Column(nullable = false, unique = true ,length = 100)
    @CPF
    private String cpf;

    @Column(nullable = false, length = 255)
    private String senha;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPerfil perfil;

    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDateTime.now();
    }
}
