package com.lucascostabr.repository;

import com.lucascostabr.domain.Tecnico;
import com.lucascostabr.enums.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TecnicoRepository extends JpaRepository<Tecnico, Long> {
    Optional<Tecnico> findFirstBySetor(Categoria categoria);

    Page<Tecnico> findAll(Pageable pageable);
}
