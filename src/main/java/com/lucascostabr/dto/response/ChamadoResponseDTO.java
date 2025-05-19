package com.lucascostabr.dto.response;

import com.lucascostabr.enums.Categoria;
import com.lucascostabr.enums.Prioridade;
import com.lucascostabr.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class ChamadoResponseDTO extends RepresentationModel<ChamadoResponseDTO> {
    private Long id;
    private String titulo;
    private String descricao;
    private LocalDateTime dataAbertura;
    private LocalDateTime dataFechamento;
    private Status status;
    private Prioridade prioridade;
    private Categoria categoria;
    private ClienteResponseDTO cliente;
    private TecnicoResponseDTO tecnico;
    private List<AnexoResponseDTO> anexos;
}
