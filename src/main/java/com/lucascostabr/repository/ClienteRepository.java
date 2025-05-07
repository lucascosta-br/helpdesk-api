package com.lucascostabr.repository;

import com.lucascostabr.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findByEmpresa(String empresa);
}
