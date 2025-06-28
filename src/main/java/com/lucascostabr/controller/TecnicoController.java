package com.lucascostabr.controller;

import com.lucascostabr.dto.request.TecnicoRequestDTO;
import com.lucascostabr.dto.request.TecnicoUpdateRequestDTO;
import com.lucascostabr.dto.response.TecnicoResponseDTO;
import com.lucascostabr.exception.BadRequestException;
import com.lucascostabr.exception.FileStorageException;
import com.lucascostabr.file.exporter.MediaTypes;
import com.lucascostabr.service.TecnicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/tecnicos")
@Tag(name = "Técnicos", description = "Endpoints para gestão de técnicos")
@RequiredArgsConstructor
public class TecnicoController {

    private final TecnicoService tecnicoService;

    @Operation(summary = "Criar técnico", description = "Cadastra um novo técnico")
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

    @Operation(summary = "Criar vários técnicos via planilha", description = "Cria técnicos a partir de um arquivo CSV ou Excel")
    @PostMapping(path = "/criarVarios", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<?> criarVarios(@RequestParam("arquivo") MultipartFile arquivo) {
        try {
            List<TecnicoResponseDTO> response = tecnicoService.criarVarios(arquivo);
            return ResponseEntity.ok(response);
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (FileStorageException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado: " + e.getMessage());
        }
    }

    @Operation(summary = "Listar técnicos", description = "Retorna todos os técnicos paginados")
    @GetMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<PagedModel<EntityModel<TecnicoResponseDTO>>> listarTodos(
            @PageableDefault(
                    page = 0,
                    size = 12,
                    sort = "id",
                    direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(tecnicoService.listarTodos(pageable));
    }

    @Operation(summary = "Exportar técnicos", description = "Exporta técnicos para XLSX ou CSV")
    @GetMapping(path = "/exportarTodos",
            produces = {
            MediaTypes.APPLICATION_XLSX_VALUE, MediaTypes.APPLICATION_CSV_VALUE
            })
    public ResponseEntity<Resource> exportarTodos(
            @PageableDefault(
                    page = 0,
                    size = 12,
                    sort = "id",
                    direction = Sort.Direction.ASC) Pageable pageable, HttpServletRequest request) {

        String acceptHeader = request.getHeader(HttpHeaders.ACCEPT);
        Resource resource = tecnicoService.exportarTodos(pageable, acceptHeader);

        Map<String, String> mapExtensao = Map.of(
                MediaTypes.APPLICATION_XLSX_VALUE, ".xlsx",
                MediaTypes.APPLICATION_CSV_VALUE, ".csv"
        );

        var contentType = acceptHeader != null ? acceptHeader : "application/octet-stream";
        var extensaoArquivo = mapExtensao.getOrDefault(acceptHeader, "");
        var nomeArquivo = "tecnicos_exportados" + extensaoArquivo;

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nomeArquivo + "\"")
                .body(resource);
    }

    @Operation(summary = "Buscar técnico por ID", description = "Retorna os dados de um técnico específico")
    @GetMapping(value = "/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<TecnicoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tecnicoService.buscarPorId(id));
    }

    @Operation(summary = "Atualizar técnico", description = "Atualiza os dados de um técnico")
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

    @Operation(summary = "Deletar técnico", description = "Exclui um técnico")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        tecnicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
