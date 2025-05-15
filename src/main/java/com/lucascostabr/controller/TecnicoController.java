package com.lucascostabr.controller;

import com.lucascostabr.dto.request.TecnicoRequestDTO;
import com.lucascostabr.dto.request.TecnicoUpdateRequestDTO;
import com.lucascostabr.dto.response.TecnicoResponseDTO;
import com.lucascostabr.service.TecnicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tecnicos")
@RequiredArgsConstructor
public class TecnicoController {

    private final TecnicoService tecnicoService;

    @PostMapping(consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    }, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<TecnicoResponseDTO> criar(@RequestBody @Valid TecnicoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tecnicoService.criar(dto));
    }

    @GetMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<Page<TecnicoResponseDTO>> listarTodos(
            @PageableDefault(
                    page = 0,
                    size = 12,
                    sort = "id",
                    direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(tecnicoService.listarTodos(pageable));
    }

    @GetMapping(value = "/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<TecnicoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tecnicoService.buscarPorId(id));
    }

    @PutMapping(value = "/{id}", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    }, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<TecnicoResponseDTO> atualizar(@PathVariable Long id,
                                                        @RequestBody @Valid TecnicoUpdateRequestDTO dto) {
        return ResponseEntity.ok(tecnicoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        tecnicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
