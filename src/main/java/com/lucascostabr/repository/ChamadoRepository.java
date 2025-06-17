package com.lucascostabr.repository;

import com.lucascostabr.domain.Chamado;
import com.lucascostabr.dto.relatorio.RelatorioCategoriaDTO;
import com.lucascostabr.dto.relatorio.RelatorioPrioridadeDTO;
import com.lucascostabr.dto.relatorio.RelatorioStatusDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {
    @Query("""
    SELECT RelatorioStatusDTO(c.status, COUNT(c))
    FROM Chamado c
    GROUP BY c.status
""")
    List<RelatorioStatusDTO> obterQuantidadeDeChamadosPorStatus();

    @Query("""
    SELECT RelatorioPrioridadeDTO(c.prioridade, COUNT(c))
    FROM Chamado c
    GROUP BY c.prioridade
""")
    List<RelatorioPrioridadeDTO> obterQuantidadeDeChamadosPorPrioridade();

    @Query("""
    SELECT RelatorioCategoriaDTO(c.categoria, COUNT(c))
    FROM Chamado c
    GROUP BY c.categoria
""")
    List<RelatorioCategoriaDTO> obterQuantidadeDeChamadosPorCategoria();

    Page<Chamado> findAll(Pageable pageable);
}
