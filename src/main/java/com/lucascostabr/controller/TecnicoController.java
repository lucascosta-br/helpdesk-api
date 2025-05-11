package com.lucascostabr.controller;

import com.lucascostabr.domain.Tecnico;
import com.lucascostabr.dto.request.TecnicoRequestDTO;
import com.lucascostabr.dto.response.TecnicoResponseDTO;
import com.lucascostabr.service.TecnicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tecnicos")
@RequiredArgsConstructor
public class TecnicoController {

    private final TecnicoService tecnicoService;

    @PostMapping
    public ResponseEntity<TecnicoResponseDTO> criar(@RequestBody @Valid TecnicoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tecnicoService.criar(dto));
    }

    @GetMapping
    public ResponseEntity<List<TecnicoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(tecnicoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tecnico> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tecnicoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TecnicoResponseDTO> atualizar(@PathVariable Long id,
                                                        @RequestBody @Valid TecnicoRequestDTO dto) {
        return ResponseEntity.ok(tecnicoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        tecnicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
