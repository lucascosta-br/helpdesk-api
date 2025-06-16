package com.lucascostabr.service;

import com.lucascostabr.controller.TecnicoController;
import com.lucascostabr.domain.Tecnico;
import com.lucascostabr.dto.request.TecnicoRequestDTO;
import com.lucascostabr.dto.request.TecnicoUpdateRequestDTO;
import com.lucascostabr.dto.response.TecnicoResponseDTO;
import com.lucascostabr.enums.Categoria;
import com.lucascostabr.enums.TipoPerfil;
import com.lucascostabr.exception.BadRequestException;
import com.lucascostabr.exception.ExportNotFoundException;
import com.lucascostabr.exception.FileStorageException;
import com.lucascostabr.exception.ResourceNotFoundException;
import com.lucascostabr.file.exporter.contract.ExportadorArquivoTecnico;
import com.lucascostabr.file.exporter.factory.ExportadorArquivoTecnicoFactory;
import com.lucascostabr.file.importer.contract.ImportadorArquivoTecnico;
import com.lucascostabr.file.importer.factory.ImportadorArquivoTecnicoFactory;
import com.lucascostabr.mapper.TecnicoMapper;
import com.lucascostabr.repository.TecnicoRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class TecnicoService {

    private final Logger logger = LoggerFactory.getLogger(TecnicoService.class);

    private final TecnicoRepository tecnicoRepository;
    private final TecnicoMapper tecnicoMapper;
    private final PagedResourcesAssembler<TecnicoResponseDTO> assembler;
    private final ImportadorArquivoTecnicoFactory importadorArquivoTecnicoFactory;
    private final ExportadorArquivoTecnicoFactory exportadorArquivoTecnicoFactory;

    public TecnicoResponseDTO criar(TecnicoRequestDTO dto) {
        logger.info("Criando um Técnico!");

        Tecnico entity = tecnicoMapper.toEntity(dto);
        entity.setPerfil(TipoPerfil.TECNICO);
        Tecnico tecnicoSalvo = tecnicoRepository.save(entity);
        var responseDTO = tecnicoMapper.toDTO(tecnicoSalvo);
        adicionarLinks(responseDTO);
        return responseDTO;
    }

    public PagedModel<EntityModel<TecnicoResponseDTO>> listarTodos(Pageable pageable) {
        logger.info("Listando todos os Técnicos com Paginação e Ordenação!");

        var tecnico = tecnicoRepository.findAll(pageable);
        return gerarModeloPaginado(pageable, tecnico);
    }

    public TecnicoResponseDTO buscarPorId(Long id) {
        logger.info("Buscando um Técnico!");

        var dto = tecnicoRepository.findById(id)
                .map(tecnicoMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Técnico não encontrado"));
        adicionarLinks(dto);
        return dto;
    }

    public TecnicoResponseDTO atualizar(Long id, TecnicoUpdateRequestDTO dto) {
        logger.info("Atualizando um Técnico!");

        Tecnico entity = tecnicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Técnico não encontrado"));

        entity.setNome(dto.nome());
        entity.setEmail(dto.email());
        entity.setSenha(dto.senha());
        entity.setSetor(dto.setor());

        var entitySalvo = tecnicoRepository.save(entity);

        var responseDTO = tecnicoMapper.toDTO(entitySalvo);
        adicionarLinks(responseDTO);
        return responseDTO;
    }

    public void deletar(Long id) {
        logger.info("Deletando um Técnico!");

        tecnicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Técnico não encontrado"));

        tecnicoRepository.deleteById(id);
    }

    public Optional<Tecnico> buscarPorCategoria(Categoria categoria) {
        return tecnicoRepository.findFirstBySetor(categoria);
    }

    public List<TecnicoResponseDTO> criarVarios(MultipartFile arquivo) {
        logger.info("Importando vários Técnicos via arquivos!");

        if (arquivo == null || arquivo.isEmpty()) {
            throw new BadRequestException("Por favor envie um arquivo válido");
        }

        String nomeArquivo = Optional.ofNullable(arquivo.getOriginalFilename())
                .orElseThrow(() -> new BadRequestException("O nome do arquivo não pode ser nulo"));

        if (!nomeArquivo.toLowerCase().endsWith(".csv") && !nomeArquivo.toLowerCase().endsWith(".xlsx")) {
            throw new BadRequestException("Formato de arquivo inválido. Use CSV ou XLSX");
        }

        try (InputStream inputStream = arquivo.getInputStream()) {
            ImportadorArquivoTecnico importador = importadorArquivoTecnicoFactory.buscarImportador(nomeArquivo);
            List<TecnicoRequestDTO> dtos = importador.importarArquivo(inputStream);

            // Valida se há dados para importar
            if (dtos.isEmpty()) {
                throw new BadRequestException("O arquivo não contém dados para importação");
            }

            return dtos.stream()
                    .map(dto -> {
                        Tecnico entity = tecnicoMapper.toEntity(dto);
                        entity.setPerfil(TipoPerfil.TECNICO);
                        Tecnico saved = tecnicoRepository.save(entity);
                        TecnicoResponseDTO response = tecnicoMapper.toDTO(saved);
                        adicionarLinks(response);
                        return response;
                    })
                    .collect(Collectors.toList());
        } catch (BadRequestException e) {
            throw e; // Repassa exceções de negócio
        } catch (Exception e) {
            logger.error("Erro ao importar técnicos: " + e.getMessage(), e);

            throw new FileStorageException("Erro ao processar o arquivo: " + e.getMessage(), e);
        }

    }

    public Resource exportarTodos(Pageable pageable, String acceptHeader) {
        logger.info("Exportando todos os Técnicos!");

        var tecnico = tecnicoRepository.findAll(pageable)
                .map(tecnicoMapper::toDTO).getContent();

        try {
            ExportadorArquivoTecnico exportador = this.exportadorArquivoTecnicoFactory.buscarExportador(acceptHeader);
            return exportador.exportadorArquivo(tecnico);
        } catch (Exception e) {
            throw new ExportNotFoundException("Erro durante a exportação de arquivo", e);
        }
    }

    private PagedModel<EntityModel<TecnicoResponseDTO>> gerarModeloPaginado(Pageable pageable, Page<Tecnico> tecnicos) {
        var tecnicoComLinks = tecnicos.map(tec -> {
            var responseDto = tecnicoMapper.toDTO(tec);
            adicionarLinks(responseDto);
            return responseDto;
        });

        Link listarTodosLinks = linkTo(methodOn(TecnicoController.class).listarTodos(pageable)).withSelfRel();

        return assembler.toModel(tecnicoComLinks, listarTodosLinks);
    }

    private void adicionarLinks(TecnicoResponseDTO dto) {
        dto.add(linkTo(methodOn(TecnicoController.class).criar(null)).withRel("criar").withType("POST"));
        dto.add(linkTo(methodOn(TecnicoController.class)).slash("criarVarios").withRel("criarVarios").withType("POST"));
        dto.add(linkTo(methodOn(TecnicoController.class).listarTodos(null)).withRel("listarTodos").withType("GET"));
        dto.add(linkTo(methodOn(TecnicoController.class).buscarPorId(dto.getId())).withRel("buscarPorId").withType("GET"));
        dto.add(linkTo(methodOn(TecnicoController.class).atualizar(dto.getId(), null)).withRel("atualizar").withType("PUT"));
        dto.add(linkTo(methodOn(TecnicoController.class).deletar(dto.getId())).withRel("deletar").withType("DELETE"));
        dto.add(linkTo(methodOn(TecnicoController.class).exportarTodos(null, null)).withRel("exportarTodos").withType("GET"));
    }

}
