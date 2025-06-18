package com.lucascostabr.repository;

import com.lucascostabr.domain.Chamado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {
    Page<Chamado> findAll(Pageable pageable);
}
