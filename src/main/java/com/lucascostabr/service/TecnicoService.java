package com.lucascostabr.service;

import com.lucascostabr.controller.TecnicoController;
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
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class TecnicoService {

    private final Logger logger = LoggerFactory.getLogger(TecnicoService.class);

    private final TecnicoRepository tecnicoRepository;
    private final TecnicoMapper tecnicoMapper;
    private final PagedResourcesAssembler<TecnicoResponseDTO> assembler;

    public TecnicoResponseDTO criar(TecnicoRequestDTO dto) {
        logger.info("Criando um Técnico!");

        Tecnico entity = tecnicoMapper.toEntity(dto);
        entity.setPerfil(TipoPerfil.TECNICO);
        Tecnico tecnicoSalvo = tecnicoRepository.save(entity);
        var responseDTO = tecnicoMapper.toDTO(tecnicoSalvo);
        adicionarLinks(responseDTO);
        return responseDTO;
    }

    public PagedModel<EntityModel<TecnicoResponseDTO>> listarTodos(Pageable pageable) {
        logger.info("Listando todos os Técnicos com Paginação e Ordenação!");

        var tecnico = tecnicoRepository.findAll(pageable);
        return gerarModeloPaginado(pageable, tecnico);
    }

    public TecnicoResponseDTO buscarPorId(Long id) {
        logger.info("Buscando um Técnico!");

        var dto = tecnicoRepository.findById(id)
                .map(tecnicoMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Técnico não encontrado"));
        adicionarLinks(dto);
        return dto;
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

        var responseDTO = tecnicoMapper.toDTO(entitySalvo);
        adicionarLinks(responseDTO);
        return responseDTO;
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

    private PagedModel<EntityModel<TecnicoResponseDTO>> gerarModeloPaginado(Pageable pageable, Page<Tecnico> tecnicos) {
        var tecnicoComLinks = tecnicos.map(tec -> {
            var responseDto = tecnicoMapper.toDTO(tec);
            adicionarLinks(responseDto);
            return responseDto;
        });

        Link listarTodosLinks = linkTo(methodOn(TecnicoController.class).listarTodos(pageable)).withSelfRel();

        return assembler.toModel(tecnicoComLinks, listarTodosLinks);
    }

    private void adicionarLinks(TecnicoResponseDTO dto) {
        dto.add(linkTo(methodOn(TecnicoController.class).criar(null)).withRel("criar").withType("POST"));
        dto.add(linkTo(methodOn(TecnicoController.class).listarTodos(null)).withRel("listarTodos").withType("GET"));
        dto.add(linkTo(methodOn(TecnicoController.class).buscarPorId(dto.getId())).withRel("buscarPorId").withType("GET"));
        dto.add(linkTo(methodOn(TecnicoController.class).atualizar(dto.getId(), null)).withRel("atualizar").withType("PUT"));
        dto.add(linkTo(methodOn(TecnicoController.class).deletar(dto.getId())).withRel("deletar").withType("DELETE"));
    }

}
