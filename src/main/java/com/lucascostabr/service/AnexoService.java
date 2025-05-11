package com.lucascostabr.service;

import com.lucascostabr.domain.Anexo;
import com.lucascostabr.domain.Chamado;
import com.lucascostabr.dto.response.AnexoResponseDTO;
import com.lucascostabr.repository.AnexoRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnexoService {

    private final Logger logger = LoggerFactory.getLogger(AnexoService.class);

    private final AnexoRepository anexoRepository;

    public List<Anexo> salvar(List<MultipartFile> arquivos, Chamado chamado) {
        List<Anexo> anexos = new ArrayList<>();

        for (MultipartFile arquivo : arquivos) {
            try {
                Anexo anexo = new Anexo();
                anexo.setNomeArquivo(arquivo.getOriginalFilename());
                anexo.setDados(arquivo.getBytes());
                anexo.setChamado(chamado);
                anexos.add(anexo);
            } catch (IOException e) {
                throw new RuntimeException("Erro ao salvar anexo", e);
            }
        }

        return anexoRepository.saveAll(anexos);
    }

    public List<AnexoResponseDTO> listarPorChamado(Long chamadoId) {
        return anexoRepository.findByChamadoId(chamadoId)
                .stream()
                .map(anexo -> new AnexoResponseDTO(
                        anexo.getId(),
                        anexo.getNomeArquivo()
                ))
                .toList();
    }

}
