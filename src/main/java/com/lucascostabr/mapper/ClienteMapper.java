package com.lucascostabr.mapper;

import com.lucascostabr.domain.Cliente;
import com.lucascostabr.dto.request.ClienteRequestDTO;
import com.lucascostabr.dto.response.ClienteResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "perfil", ignore = true)
    @Mapping(target = "chamados", ignore = true)
    Cliente toEntity(ClienteRequestDTO dto);

    ClienteResponseDTO toDTO(Cliente entity);
}
