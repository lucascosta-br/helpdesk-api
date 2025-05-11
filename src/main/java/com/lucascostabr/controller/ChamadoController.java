package com.lucascostabr.controller;

import com.lucascostabr.domain.Chamado;
import com.lucascostabr.dto.request.ChamadoRequestDTO;
import com.lucascostabr.dto.request.ChamadoStatusRequestDTO;
import com.lucascostabr.dto.response.ChamadoResponseDTO;
import com.lucascostabr.service.ChamadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(name = "/api/v1/chamados")
@RequiredArgsConstructor
public class ChamadoController {

    private final ChamadoService chamadoService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ChamadoResponseDTO> criar(@RequestPart("chamado") ChamadoRequestDTO dto,
                                                    @RequestPart(value = "arquivos", required = false)List<MultipartFile> arquivos) {
        return ResponseEntity.status(HttpStatus.CREATED).body(chamadoService.criar(dto, arquivos));
    }

    @GetMapping
    public ResponseEntity<List<ChamadoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(chamadoService.listarTodos());
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<Chamado> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(chamadoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChamadoResponseDTO> atualizar(@PathVariable Long id,
                                                        @RequestBody @Valid ChamadoStatusRequestDTO dto) {
        ChamadoResponseDTO response = chamadoService.atualizar(id, dto.status());

        return ResponseEntity.ok(response);
    }


}
