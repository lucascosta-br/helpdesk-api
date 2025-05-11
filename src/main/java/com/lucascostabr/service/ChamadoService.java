package com.lucascostabr.service;

import com.lucascostabr.domain.Anexo;
import com.lucascostabr.domain.Chamado;
import com.lucascostabr.domain.Cliente;
import com.lucascostabr.domain.Tecnico;
import com.lucascostabr.dto.request.ChamadoRequestDTO;
import com.lucascostabr.dto.response.ChamadoResponseDTO;
import com.lucascostabr.mapper.ChamadoMapper;
import com.lucascostabr.repository.ChamadoRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChamadoService {

    private final Logger logger = LoggerFactory.getLogger(ChamadoService.class);

    private final ChamadoRepository chamadoRepository;
    private final ChamadoMapper chamadoMapper;
    private final ClienteService clienteService;
    private final TecnicoService tecnicoService;
    private final AnexoService anexoService;


    public ChamadoResponseDTO criar(ChamadoRequestDTO dto, List<MultipartFile> arquivos) {
        logger.info("Criando um Chamado!");

        Chamado chamado = chamadoMapper.toEntity(dto);
        Cliente cliente = clienteService.buscarPorId(dto.clienteId());
        Tecnico tecnico = tecnicoService.buscarPorId(dto.tecnicoId());
        chamado.setCliente(cliente);
        chamado.setTecnico(tecnico);

        Chamado chamadoSalvo = chamadoRepository.save(chamado);

        if (arquivos != null && !arquivos.isEmpty()) {
            List<Anexo> anexos = anexoService.salvar(arquivos, chamadoSalvo);
            chamadoSalvo.setAnexos(anexos);
            chamadoSalvo = chamadoRepository.save(chamadoSalvo); // atualizar com anexos
        }

        return chamadoMapper.toDTO(chamadoSalvo);

    }

    public List<ChamadoResponseDTO> listarTodos() {
        return chamadoRepository.findAll()
                .stream()
                .map(chamadoMapper::toDTO)
                .toList();
    }

    public Chamado buscarPorId(Long id) {
        return chamadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chamado não encontrado"));
    }

}
