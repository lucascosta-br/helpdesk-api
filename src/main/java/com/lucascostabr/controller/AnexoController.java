package com.lucascostabr.controller;

import com.lucascostabr.dto.response.AnexoResponseDTO;
import com.lucascostabr.service.AnexoService;
import com.lucascostabr.service.ArmazenamentoArquivoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/anexos")
@RequiredArgsConstructor
public class AnexoController {
    private static final Logger logger = LoggerFactory.getLogger(AnexoController.class);

    private final AnexoService anexoService;
    private final ArmazenamentoArquivoService arquivoService;

    @PostMapping("/upload/{chamadoId}")
    public ResponseEntity<AnexoResponseDTO> uploadArquivo(@PathVariable Long chamadoId,
                                                          @RequestParam("arquivo") MultipartFile arquivo) {
       var anexo = anexoService.salvar(chamadoId, arquivo);
       return ResponseEntity.status(HttpStatus.CREATED).body(anexo);
    }

    @PostMapping("/uploadMultiplos/{chamadoId}")
    public List<ResponseEntity<AnexoResponseDTO>> uploadMultiplos(@PathVariable Long chamadoId,
                                                                  @RequestParam("arquivos") MultipartFile[] arquivos) {
        return Arrays.asList(arquivos)
                .stream()
                .map(file -> uploadArquivo(chamadoId, file))
                .collect(Collectors.toList());
    }

    @GetMapping("/por-chamado/{chamadoId}")
    public ResponseEntity<List<AnexoResponseDTO>> listarPorChamado(@PathVariable Long chamadoId) {
        var anexos = anexoService.listarPorChamado(chamadoId);
        return ResponseEntity.ok(anexos);
    }

    @GetMapping("/downloadFile/{nomeArquivo:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String nomeArquivo, HttpServletRequest request) {
        Resource resource = arquivoService.carregarArquivo(nomeArquivo);
        String contentType = null;

        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (Exception e) {
            logger.error("Could not determine file type!");
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; nomeArquivo=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
