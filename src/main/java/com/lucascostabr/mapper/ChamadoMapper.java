package com.lucascostabr.mapper;

import com.lucascostabr.domain.Chamado;
import com.lucascostabr.dto.request.ChamadoRequestDTO;
import com.lucascostabr.dto.response.ChamadoResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChamadoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", expression = "java(com.lucascostabr.enums.Status.ABERTO)")
    @Mapping(target = "dataAbertura", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "dataFechamento", ignore = true)
    @Mapping(target = "anexos", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "tecnico", ignore = true)
    Chamado toEntity(ChamadoRequestDTO dto);

    ChamadoResponseDTO toDTO(Chamado entity);

}
