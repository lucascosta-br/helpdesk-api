package com.lucascostabr.repository;

import com.lucascostabr.domain.Tecnico;
import com.lucascostabr.enums.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TecnicoRepository extends JpaRepository<Tecnico, Long> {
    List<Tecnico> findBySetor(String setor);

    Optional<Tecnico> findFirstBySetor(Categoria categoria);
}
