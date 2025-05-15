package com.lucascostabr.service;

import com.lucascostabr.domain.Tecnico;
import com.lucascostabr.dto.request.TecnicoRequestDTO;
import com.lucascostabr.dto.request.TecnicoUpdateRequestDTO;
import com.lucascostabr.dto.response.TecnicoResponseDTO;
import com.lucascostabr.enums.Categoria;
import com.lucascostabr.enums.TipoPerfil;
import com.lucascostabr.exception.ResourceNotFoundException;
import com.lucascostabr.mapper.TecnicoMapper;
import com.lucascostabr.repository.TecnicoRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Page<TecnicoResponseDTO> listarTodos(Pageable pageable) {
        logger.info("Listando todos os Técnicos com Paginação e Ordenação!");

        return tecnicoRepository.findAll(pageable)
                .map(tecnicoMapper::toDTO);
    }

    public TecnicoResponseDTO buscarPorId(Long id) {
        logger.info("Buscando um Técnico!");

        return tecnicoRepository.findById(id)
                .map(tecnicoMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Técnico não encontrado"));
    }

    public TecnicoResponseDTO atualizar(Long id, TecnicoUpdateRequestDTO dto) {
        logger.info("Atualizando um Técnico!");

        Tecnico entity = tecnicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Técnico não encontrado"));

        entity.setNome(dto.nome());
        entity.setEmail(dto.email());
        entity.setSenha(dto.senha());
        entity.setSetor(dto.setor());

        var entitySalvo = tecnicoRepository.save(entity);

        return tecnicoMapper.toDTO(entitySalvo);
    }

    public void deletar(Long id) {
        logger.info("Deletando um Técnico!");

        tecnicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Técnico não encontrado"));

        tecnicoRepository.deleteById(id);
    }

    public Optional<Tecnico> buscarPorCategoria(Categoria categoria) {
        return tecnicoRepository.findFirstBySetor(categoria);
    }

}
