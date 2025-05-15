package com.lucascostabr.service;

import com.lucascostabr.domain.Chamado;
import com.lucascostabr.domain.Cliente;
import com.lucascostabr.domain.Tecnico;
import com.lucascostabr.dto.request.ChamadoRequestDTO;
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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChamadoService {

    private final Logger logger = LoggerFactory.getLogger(ChamadoService.class);

    private final ChamadoRepository chamadoRepository;
    private final ChamadoMapper chamadoMapper;
    private final ClienteService clienteService;
    private final TecnicoService tecnicoService;

    public ChamadoResponseDTO criar(ChamadoRequestDTO dto) {
        logger.info("Criando um Chamado!");

        Chamado chamado = chamadoMapper.toEntity(dto);
        Cliente cliente = clienteService.buscarPorId(dto.clienteId());
        chamado.setCliente(cliente);

        Tecnico tecnico = tecnicoService.buscarPorCategoria(dto.categoria())
                        .orElseThrow(() -> new BusinessException("Nenhum técnico disponível para a categoria: " + dto.categoria()));
        chamado.setTecnico(tecnico);

        Chamado chamadoSalvo = chamadoRepository.save(chamado);
        return chamadoMapper.toDTO(chamadoSalvo);
    }

    public Page<ChamadoResponseDTO> listarTodos(Pageable pageable) {
        logger.info("Listando Chamados com Paginação e Ordenação!");

        return chamadoRepository.findAll(pageable)
                .map(chamadoMapper::toDTO);
    }

    public Chamado buscarPorId(Long id) {
        logger.info("Buscando um Chamado!");

        return chamadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chamado não encontrado"));
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

        return chamadoMapper.toDTO(chamadoAtualizado);
    }

}
