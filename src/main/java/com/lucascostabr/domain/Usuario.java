package com.lucascostabr.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lucascostabr.enums.TipoPerfil;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Usuario implements Serializable, UserDetails {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true ,length = 100)
    @Email
    private String email;

    @Column(nullable = false, unique = true ,length = 100)
    @CPF
    private String cpf;

    @Column(nullable = false, length = 255)
    @JsonIgnore
    private String senha;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPerfil perfil;

    public Usuario(String email, String senha, TipoPerfil perfil) {
        this.email = email;
        this.senha = senha;
        this.perfil = perfil;
    }

    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDateTime.now();
    }

    // Implementação do UserDetails

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.perfil == TipoPerfil.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_CLIENTE"),
                    new SimpleGrantedAuthority("ROLE_TECNICO"));
        } else if (this.perfil == TipoPerfil.CLIENTE) {
            return List.of(new SimpleGrantedAuthority("ROLE_CLIENTE"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_TECNICO"));
        }
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
