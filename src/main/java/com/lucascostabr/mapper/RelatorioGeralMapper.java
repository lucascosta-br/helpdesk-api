package com.lucascostabr.mapper;

import com.lucascostabr.domain.Chamado;
import com.lucascostabr.dto.relatorio.RelatorioGeralDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface RelatorioGeralMapper {

    @Mapping(target = "status", source = "status", qualifiedByName = "enumToString")
    @Mapping(target = "prioridade", source = "prioridade", qualifiedByName = "enumToString")
    @Mapping(target = "categoria", source = "categoria", qualifiedByName = "enumToString")
    @Mapping(target = "dataAbertura", source = "dataAbertura", qualifiedByName = "localDateTimeToTimestamp")
    @Mapping(target = "dataFechamento", source = "dataFechamento", qualifiedByName = "localDateTimeToTimestamp")
    RelatorioGeralDTO toRelatorioGeralDTO(Chamado chamado);

    @Named("enumToString")
    default String enumToString(Enum<?> enumValue) {
        return enumValue != null ? enumValue.name() : null;
    }

    @Named("localDateTimeToTimestamp")
    default Timestamp localDateTimeToTimestamp(LocalDateTime localDateTime) {
        return localDateTime != null ? Timestamp.valueOf(localDateTime) : null;
    }
}


