package com.lucascostabr.service;

import com.lucascostabr.domain.Anexo;
import com.lucascostabr.domain.Chamado;
import com.lucascostabr.dto.response.AnexoResponseDTO;
import com.lucascostabr.exception.ResourceNotFoundException;
import com.lucascostabr.mapper.AnexoMapper;
import com.lucascostabr.repository.AnexoRepository;
import com.lucascostabr.repository.ChamadoRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnexoService {

    private static final Logger logger = LoggerFactory.getLogger(AnexoService.class);

    private final AnexoRepository anexoRepository;
    private final AnexoMapper anexoMapper;
    private final ChamadoRepository chamadoRepository;
    private final ArmazenamentoArquivoService armazenamentoArquivoService;

    public AnexoResponseDTO salvar(Long chamadoId, MultipartFile arquivo) {
        logger.info("Salvando Anexo");

        Chamado chamado = chamadoRepository.findById(chamadoId)
                .orElseThrow(() -> new ResourceNotFoundException("Chamado não encontrado"));

        String nomeArquivo = armazenamentoArquivoService.armazenarArquivo(arquivo);

        Anexo anexo = new Anexo();
        anexo.setNome(nomeArquivo);
        anexo.setTipo(arquivo.getContentType());
        anexo.setCaminho(nomeArquivo);
        anexo.setTamanho(arquivo.getSize());
        anexo.setChamado(chamado);

        Anexo anexoSalvo = anexoRepository.save(anexo);
        return anexoMapper.toDTO(anexoSalvo);
    }

    public List<AnexoResponseDTO> listarPorChamado(Long chamadoId) {
        logger.info("Listando Anexo por Chamado");

        return anexoRepository.findByChamadoId(chamadoId)
                .stream()
                .map(anexo -> new AnexoResponseDTO(
                        anexo.getId(),
                        anexo.getNome(),
                        anexo.getTipo(),
                        anexo.getCaminho(),
                        anexo.getTamanho()
                ))
                .toList();
    }

}
