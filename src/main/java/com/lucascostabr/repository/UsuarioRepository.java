package com.lucascostabr.repository;

import com.lucascostabr.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    //Optional<Usuario> findByEmail(String email);
    UserDetails findByEmail(String email);
}
