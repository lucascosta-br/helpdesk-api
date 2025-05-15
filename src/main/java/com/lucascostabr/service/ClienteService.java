package com.lucascostabr.service;

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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final Logger logger = LoggerFactory.getLogger(ClienteService.class);

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    public ClienteResponseDTO criar(ClienteRequestDTO dto) {
        logger.info("Criando um Cliente!");

        Cliente entity = clienteMapper.toEntity(dto);
        entity.setPerfil(TipoPerfil.CLIENTE);
        Cliente clienteSalvo = clienteRepository.save(entity);
        return clienteMapper.toDTO(clienteSalvo);
    }

    public Page<ClienteResponseDTO> listarTodos(Pageable pageable) {
        logger.info("Listando todos os Clientes com Paginação e Ordenação!");

        return clienteRepository.findAll(pageable)
                .map(clienteMapper::toDTO);
    }

    public ClienteResponseDTO buscarPorId(Long id) {
        logger.info("Buscando um Cliente");

        return clienteRepository.findById(id)
                .map(clienteMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
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

        return clienteMapper.toDTO(entitySalvo);
    }

    public void deletar(Long id) {
        logger.info("Deletando um Cliente");

        clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        clienteRepository.deleteById(id);
    }

}
