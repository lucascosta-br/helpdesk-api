package com.lucascostabr.repository;

import com.lucascostabr.domain.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TecnicoRepository extends JpaRepository<Tecnico, Long> {
    List<Tecnico> findBySetor(String setor);
}
