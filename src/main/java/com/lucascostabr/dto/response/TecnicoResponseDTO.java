package com.lucascostabr.dto.response;

import com.lucascostabr.enums.Categoria;
import com.lucascostabr.enums.TipoPerfil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class TecnicoResponseDTO extends RepresentationModel<TecnicoResponseDTO> {
    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private Categoria setor;
    private TipoPerfil perfil;
}
