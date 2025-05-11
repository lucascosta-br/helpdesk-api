package com.lucascostabr.mapper;

import com.lucascostabr.domain.Tecnico;
import com.lucascostabr.dto.request.TecnicoRequestDTO;
import com.lucascostabr.dto.response.TecnicoResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TecnicoMapper {

    Tecnico toEntity(TecnicoRequestDTO dto);

    TecnicoResponseDTO toDTO(Tecnico entity);

}
