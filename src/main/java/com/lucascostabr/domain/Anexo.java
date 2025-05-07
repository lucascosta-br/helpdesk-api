package com.lucascostabr.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "anexos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Anexo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_arquivo", nullable = false, length = 255)
    private String nomeArquivo;

    @Lob
    private byte[] dados;

    @ManyToOne
    @JoinColumn(name = "chamado_id")
    private Chamado chamado;
}
