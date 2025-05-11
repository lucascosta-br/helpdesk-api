package com.lucascostabr.mapper;

import com.lucascostabr.domain.Anexo;
import com.lucascostabr.dto.response.AnexoResponseDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AnexoMapper {

    AnexoResponseDTO toDTO(Anexo entity);

    List<AnexoResponseDTO> toDTOList(List<Anexo> anexos);

}
