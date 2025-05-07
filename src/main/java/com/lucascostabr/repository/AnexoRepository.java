package com.lucascostabr.repository;

import com.lucascostabr.domain.Anexo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnexoRepository extends JpaRepository<Anexo, Long> {
    List<Anexo> findByChamadoId(Long chamadoId);
}
