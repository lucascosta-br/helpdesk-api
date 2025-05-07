package com.lucascostabr.repository;

import com.lucascostabr.domain.Chamado;
import com.lucascostabr.enums.Prioridade;
import com.lucascostabr.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {
    List<Chamado> findByClienteId(Long clienteId);
    List<Chamado> findByTecnicoId(Long tecnicoId);
    List<Chamado> findByStatus(Status status);
    List<Chamado> findByPrioridade(Prioridade prioridade);
}
