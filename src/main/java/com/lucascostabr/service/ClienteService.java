package com.lucascostabr.service;

import com.lucascostabr.controller.ClienteController;
import com.lucascostabr.domain.Cliente;
import com.lucascostabr.dto.request.ClienteRequestDTO;
import com.lucascostabr.dto.request.ClienteUpdateRequestDTO;
import com.lucascostabr.dto.response.ClienteResponseDTO;
import com.lucascostabr.enums.TipoPerfil;
import com.lucascostabr.exception.ResourceNotFoundException;
import com.lucascostabr.mapper.ClienteMapper;
import com.lucascostabr.repository.ClienteRepository;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final Logger logger = LoggerFactory.getLogger(ClienteService.class);

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    private final PagedResourcesAssembler<ClienteResponseDTO> assembler;

    public ClienteResponseDTO criar(ClienteRequestDTO dto) {
        logger.info("Criando um Cliente!");

        Cliente entity = clienteMapper.toEntity(dto);
        entity.setPerfil(TipoPerfil.CLIENTE);
        Cliente clienteSalvo = clienteRepository.save(entity);
        var responseDTO = clienteMapper.toDTO(clienteSalvo);
        adicionarLinks(responseDTO);
        return responseDTO;
    }

    public PagedModel<EntityModel<ClienteResponseDTO>> listarTodos(Pageable pageable) {
        logger.info("Listando todos os Clientes com Paginação e Ordenação!");

        var cliente = clienteRepository.findAll(pageable);
        return gerarModeloPaginado(pageable, cliente);
    }

    public ClienteResponseDTO buscarPorId(Long id) {
        logger.info("Buscando um Cliente");

        var dto = clienteRepository.findById(id)
                .map(clienteMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
        adicionarLinks(dto);
        return dto;
    }

    public Cliente buscarEntidadePorId(Long id) {
        logger.info("Buscando Cliente ENTIDADE");

        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
    }

    public ClienteResponseDTO atualizar(Long id, ClienteUpdateRequestDTO dto) {
        logger.info("Atualizando um Cliente");

        Cliente entity = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        entity.setNome(dto.nome());
        entity.setEmail(dto.email());
        entity.setSenha(dto.senha());
        entity.setEmpresa(dto.empresa());

        var entitySalvo = clienteRepository.save(entity);

        var responseDTO = clienteMapper.toDTO(entitySalvo);
        adicionarLinks(responseDTO);
        return responseDTO;
    }

    public void deletar(Long id) {
        logger.info("Deletando um Cliente");

        clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        clienteRepository.deleteById(id);
    }

    private PagedModel<EntityModel<ClienteResponseDTO>> gerarModeloPaginado(Pageable pageable, Page<Cliente> clientes) {
        var clienteComLinks = clientes.map(cli -> {
            var responseDto = clienteMapper.toDTO(cli);
            adicionarLinks(responseDto);
            return responseDto;
        });

        Link listarTodosLinks = linkTo(methodOn(ClienteController.class).listarTodos(pageable)).withSelfRel();

        return assembler.toModel(clienteComLinks, listarTodosLinks);
    }

    private void adicionarLinks(ClienteResponseDTO dto) {
        dto.add(linkTo(methodOn(ClienteController.class).criar(null)).withRel("criar").withType("POST"));
        dto.add(linkTo(methodOn(ClienteController.class).listarTodos(null)).withRel("listarTodos").withType("GET"));
        dto.add(linkTo(methodOn(ClienteController.class).buscarPorId(dto.getId())).withRel("buscarPorId").withType("GET"));
        dto.add(linkTo(methodOn(ClienteController.class).atualizar(dto.getId(), null)).withRel("atualizar").withType("PUT"));
        dto.add(linkTo(methodOn(ClienteController.class).deletar(dto.getId())).withRel("deletar").withType("DELETE"));
    }

}
