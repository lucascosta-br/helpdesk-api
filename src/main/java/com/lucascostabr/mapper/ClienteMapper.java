package com.lucascostabr.mapper;

import com.lucascostabr.domain.Cliente;
import com.lucascostabr.dto.response.ClienteResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    Cliente toEntity(ClienteResponseDTO dto);

    ClienteResponseDTO toDTO(Cliente entity);
}
