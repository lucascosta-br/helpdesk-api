package com.lucascostabr.dto.response;

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
public class ClienteResponseDTO extends RepresentationModel<ClienteResponseDTO> {
    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private String empresa;
    private TipoPerfil perfil;
}
