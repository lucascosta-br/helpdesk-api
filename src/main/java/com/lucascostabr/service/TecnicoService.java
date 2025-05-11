package com.lucascostabr.service;

import com.lucascostabr.domain.Tecnico;
import com.lucascostabr.dto.request.TecnicoRequestDTO;
import com.lucascostabr.dto.response.TecnicoResponseDTO;
import com.lucascostabr.enums.TipoPerfil;
import com.lucascostabr.mapper.TecnicoMapper;
import com.lucascostabr.repository.TecnicoRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TecnicoService {

    private final Logger logger = LoggerFactory.getLogger(TecnicoService.class);

    private final TecnicoRepository tecnicoRepository;
    private final TecnicoMapper tecnicoMapper;

    public TecnicoResponseDTO criar(TecnicoRequestDTO dto) {
        logger.info("Criando um Técnico!");

        Tecnico entity = tecnicoMapper.toEntity(dto);
        entity.setPerfil(TipoPerfil.TECNICO);
        Tecnico tecnicoSalvo = tecnicoRepository.save(entity);
        return tecnicoMapper.toDTO(tecnicoSalvo);
    }

    public List<TecnicoResponseDTO> listarTodos() {
        logger.info("Listando todos os Técnicos!");

        return tecnicoRepository.findAll()
                .stream()
                .map(tecnicoMapper::toDTO)
                .toList();
    }

    public Tecnico buscarPorId(Long id) {
        logger.info("Buscando um Técnico!");

        return tecnicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Técnico não encontrado"));
    }

}
