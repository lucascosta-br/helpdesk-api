package com.lucascostabr.controller;

import com.lucascostabr.dto.request.ChamadoRequestDTO;
import com.lucascostabr.dto.request.ChamadoStatusRequestDTO;
import com.lucascostabr.dto.response.ChamadoResponseDTO;
import com.lucascostabr.mapper.ChamadoMapper;
import com.lucascostabr.service.ChamadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chamados")
@RequiredArgsConstructor
public class ChamadoController {

    private final ChamadoService chamadoService;
    private final ChamadoMapper chamadoMapper;

    @PostMapping
    public ResponseEntity<ChamadoResponseDTO> criar(@RequestBody @Valid ChamadoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(chamadoService.criar(dto));
    }

    @GetMapping
    public ResponseEntity<List<ChamadoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(chamadoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChamadoResponseDTO> buscarPorId(@PathVariable Long id) {
        var chamado = chamadoService.buscarPorId(id);
        return ResponseEntity.ok(chamadoMapper.toDTO(chamado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChamadoResponseDTO> atualizar(@PathVariable Long id,
                                                        @RequestBody @Valid ChamadoStatusRequestDTO dto) {
        ChamadoResponseDTO response = chamadoService.atualizar(id, dto.status());

        return ResponseEntity.ok(response);
    }

}
