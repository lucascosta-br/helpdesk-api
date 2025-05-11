package com.lucascostabr.service;

import com.lucascostabr.domain.Cliente;
import com.lucascostabr.dto.request.ClienteRequestDTO;
import com.lucascostabr.dto.response.ClienteResponseDTO;
import com.lucascostabr.enums.TipoPerfil;
import com.lucascostabr.mapper.ClienteMapper;
import com.lucascostabr.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<ClienteResponseDTO> listarTodos() {
        logger.info("Listando todos os Clientes!");

        return clienteRepository.findAll()
                .stream()
                .map(clienteMapper::toDTO)
                .toList();
    }

    public Cliente buscarPorId(Long id) {
        logger.info("Buscando um Cliente");

        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    public ClienteResponseDTO atualizar(Long id, ClienteRequestDTO dto) {
        logger.info("Atualizando um Cliente");

        Cliente entity = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        if (dto == null) {
            throw new RuntimeException("Parâmetros de atualização obrigatórios");
        } // TODO: retirar depois que passar as annotations no dto request com a mensagem

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
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        clienteRepository.deleteById(id);
    }

}
