package com.lucascostabr.service;

import com.lucascostabr.controller.ChamadoController;
import com.lucascostabr.domain.Chamado;
import com.lucascostabr.domain.Cliente;
import com.lucascostabr.domain.Tecnico;
import com.lucascostabr.dto.request.ChamadoRequestDTO;
import com.lucascostabr.dto.request.ChamadoStatusRequestDTO;
import com.lucascostabr.dto.response.ChamadoResponseDTO;
import com.lucascostabr.enums.Status;
import com.lucascostabr.exception.BusinessException;
import com.lucascostabr.exception.ResourceNotFoundException;
import com.lucascostabr.mapper.ChamadoMapper;
import com.lucascostabr.repository.ChamadoRepository;
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

import java.time.LocalDateTime;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class ChamadoService {

    private final Logger logger = LoggerFactory.getLogger(ChamadoService.class);

    private final ChamadoRepository chamadoRepository;
    private final ChamadoMapper chamadoMapper;
    private final ClienteService clienteService;
    private final TecnicoService tecnicoService;
    private final PagedResourcesAssembler<ChamadoResponseDTO> assembler;

    public ChamadoResponseDTO criar(ChamadoRequestDTO dto) {
        logger.info("Criando um Chamado!");

        Chamado chamado = chamadoMapper.toEntity(dto);
        Cliente cliente = clienteService.buscarEntidadePorId(dto.clienteId());
        chamado.setCliente(cliente);

        Tecnico tecnico = tecnicoService.buscarPorCategoria(dto.categoria())
                        .orElseThrow(() -> new BusinessException("Nenhum técnico disponível para a categoria: " + dto.categoria()));
        chamado.setTecnico(tecnico);

        Chamado chamadoSalvo = chamadoRepository.save(chamado);
        var responseDTO = chamadoMapper.toDTO(chamadoSalvo);
        adicionarLinks(responseDTO);
        return responseDTO;
    }

    public PagedModel<EntityModel<ChamadoResponseDTO>> listarTodos(Pageable pageable) {
        logger.info("Listando Chamados com Paginação e Ordenação!");

        var chamado = chamadoRepository.findAll(pageable);

        return gerarModeloPaginado(pageable, chamado);
    }

    public ChamadoResponseDTO buscarPorId(Long id) {
        logger.info("Buscando um Chamado!");

        var dto = chamadoRepository.findById(id)
                .map(chamadoMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Chamado não encontrado"));
        adicionarLinks(dto);
        return dto;
    }

    public ChamadoResponseDTO atualizar(Long id, Status status) {
        logger.info("Atualizar um Chamado!");

        var chamado = chamadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chamado não encontrado"));

        Status statusAtual = chamado.getStatus();

        if (status == Status.EM_ANDAMENTO) {
            if (statusAtual != Status.ABERTO) {
                throw new BusinessException("Só é possível colcoar em ANDAMENTO um chamado que esteja ABERTO");
            }
        } else if (status == Status.CONCLUIDO) {
            if (statusAtual != Status.EM_ANDAMENTO) {
                throw new BusinessException("Só é possível CONCLUIR um chamado que esteja EM ANDAMENTO");
            }
            chamado.setDataFechamento(LocalDateTime.now());
        } else if (status == Status.ABERTO) {
            throw new BusinessException("Não é possível reabrir um chamado diretamente");
        }

        chamado.setStatus(status);
        var chamadoAtualizado = chamadoRepository.save(chamado);

        var responseDTO = chamadoMapper.toDTO(chamadoAtualizado);
        adicionarLinks(responseDTO);
        return responseDTO;
    }

    public void deletar(Long id) {
        logger.info("Deletando um Chamado");

        chamadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chamado não encontrado"));

        chamadoRepository.deleteById(id);
    }

    private PagedModel<EntityModel<ChamadoResponseDTO>> gerarModeloPaginado(Pageable pageable, Page<Chamado> chamados) {
        var chamadoComLinks = chamados.map(cha -> {
            var responseDto = chamadoMapper.toDTO(cha);
            adicionarLinks(responseDto);
            return responseDto;
        });

        Link listarTodosLinks = linkTo(methodOn(ChamadoController.class).listarTodos(pageable)).withSelfRel();

        return assembler.toModel(chamadoComLinks, listarTodosLinks);
    }

    private void adicionarLinks(ChamadoResponseDTO dto) {
        dto.add(linkTo(methodOn(ChamadoController.class).criar(null)).withRel("criar").withType("POST"));
        dto.add(linkTo(methodOn(ChamadoController.class).listarTodos(null)).withRel("listarTodos").withType("GET"));
        dto.add(linkTo(methodOn(ChamadoController.class).buscarPorId(dto.getId())).withRel("buscarPorId").withType("GET"));
        dto.add(linkTo(methodOn(ChamadoController.class).atualizar(dto.getId(), new ChamadoStatusRequestDTO(Status.ABERTO))).withRel("atualizar").withType("PUT"));
        dto.add(linkTo(methodOn(ChamadoController.class).deletar(dto.getId())).withRel("deletar").withType("DELETE"));
    }

}
