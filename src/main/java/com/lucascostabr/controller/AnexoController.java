package com.lucascostabr.controller;

import com.lucascostabr.dto.response.AnexoResponseDTO;
import com.lucascostabr.service.AnexoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/anexos")
@RequiredArgsConstructor
public class AnexoController {

    private final AnexoService anexoService;

    @PostMapping("/upload/{chamadoId}")
    public ResponseEntity<AnexoResponseDTO> uploadArquivo(@PathVariable Long chamadoId,
                                                          @RequestParam("arquivo") MultipartFile arquivo) {
       var anexo = anexoService.salvar(chamadoId, arquivo);
       return ResponseEntity.status(HttpStatus.CREATED).body(anexo);
    }

    @GetMapping("/por-chamado/{chamadoId}")
    public ResponseEntity<List<AnexoResponseDTO>> listarPorChamado(@PathVariable Long chamadoId) {
        var anexos = anexoService.listarPorChamado(chamadoId);
        return ResponseEntity.ok(anexos);
    }

}
