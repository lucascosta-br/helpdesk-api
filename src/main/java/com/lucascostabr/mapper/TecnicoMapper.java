package com.lucascostabr.mapper;

import com.lucascostabr.domain.Tecnico;
import com.lucascostabr.dto.request.TecnicoRequestDTO;
import com.lucascostabr.dto.response.TecnicoResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TecnicoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "perfil", ignore = true)
    @Mapping(target = "chamados", ignore = true)
    Tecnico toEntity(TecnicoRequestDTO dto);

    TecnicoResponseDTO toDTO(Tecnico entity);

}
