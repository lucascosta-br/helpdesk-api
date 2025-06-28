package com.lucascostabr.controller;

import com.lucascostabr.dto.request.ChamadoRequestDTO;
import com.lucascostabr.dto.request.ChamadoStatusRequestDTO;
import com.lucascostabr.dto.response.ChamadoResponseDTO;
import com.lucascostabr.service.ChamadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chamados")
@Tag(name = "Chamados", description = "Endpoints para gestão de chamados")
@RequiredArgsConstructor
public class ChamadoController {

    private final ChamadoService chamadoService;

    @Operation(summary = "Criar um chamado", description = "Cadastra um novo chamado")
    @ApiResponse(responseCode = "201", description = "Chamado criado com sucesso")
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

    @Operation(summary = "Listar chamados", description = "Retorna todos os chamados paginados")
    @ApiResponse(responseCode = "200", description = "Chamados listados com sucesso")
    @GetMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<PagedModel<EntityModel<ChamadoResponseDTO>>> listarTodos(
            @PageableDefault(
                    page = 0,
                    size = 12,
                    sort = "id",
                    direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(chamadoService.listarTodos(pageable));
    }

    @Operation(summary = "Buscar chamado por ID", description = "Retorna os dados de um chamado específico")
    @ApiResponse(responseCode = "200", description = "Chamado encontrado")
    @ApiResponse(responseCode = "404", description = "Chamado não encontrado")
    @GetMapping(value = "/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<ChamadoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(chamadoService.buscarPorId(id));
    }

    @Operation(summary = "Atualizar status do chamado", description = "Atualiza o status de um chamado")
    @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso")
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

    @Operation(summary = "Deletar chamado", description = "Exclui um chamado")
    @ApiResponse(responseCode = "204", description = "Chamado deletado com sucesso")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        chamadoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
