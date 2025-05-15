package com.lucascostabr.controller;

import com.lucascostabr.dto.request.ChamadoRequestDTO;
import com.lucascostabr.dto.request.ChamadoStatusRequestDTO;
import com.lucascostabr.dto.response.ChamadoResponseDTO;
import com.lucascostabr.service.ChamadoService;
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
@RequestMapping("/api/v1/chamados")
@RequiredArgsConstructor
public class ChamadoController {

    private final ChamadoService chamadoService;

    @PostMapping(consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    }, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<ChamadoResponseDTO> criar(@RequestBody @Valid ChamadoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(chamadoService.criar(dto));
    }

    @GetMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<Page<ChamadoResponseDTO>> listarTodos(
            @PageableDefault(
                    page = 0,
                    size = 12,
                    sort = "id",
                    direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(chamadoService.listarTodos(pageable));
    }

    @GetMapping(value = "/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<ChamadoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(chamadoService.buscarPorId(id));
    }

    @PutMapping(value = "/{id}", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    }, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<ChamadoResponseDTO> atualizar(@PathVariable Long id,
                                                        @RequestBody @Valid ChamadoStatusRequestDTO dto) {
        ChamadoResponseDTO response = chamadoService.atualizar(id, dto.status());

        return ResponseEntity.ok(response);
    }

}
