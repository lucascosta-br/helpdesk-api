package com.lucascostabr.controller;

import com.lucascostabr.domain.Cliente;
import com.lucascostabr.domain.Tecnico;
import com.lucascostabr.domain.Usuario;
import com.lucascostabr.dto.security.AutenticacaoDTO;
import com.lucascostabr.dto.security.LoginResponseDTO;
import com.lucascostabr.dto.security.RegistroDTO;
import com.lucascostabr.enums.Categoria;
import com.lucascostabr.enums.TipoPerfil;
import com.lucascostabr.repository.UsuarioRepository;
import com.lucascostabr.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/autenticacao")
public class AutorizacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AutenticacaoDTO dto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var usuario = (Usuario) auth.getPrincipal();
        var token = tokenService.generateToken(usuario);

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/registrar")
    public ResponseEntity registrar(@RequestBody @Valid RegistroDTO dto) {
        if (this.usuarioRepository.findByEmail(dto.email()) != null) {
            return ResponseEntity.badRequest().build();
        }

        var senhaEncriptada = new BCryptPasswordEncoder().encode(dto.senha());

        Usuario novoUsuario;

        if (dto.perfil() == TipoPerfil.CLIENTE) {
            novoUsuario = new Cliente();
            novoUsuario.setNome(dto.nome());
            novoUsuario.setEmail(dto.email());
            novoUsuario.setCpf(dto.cpf());
            novoUsuario.setSenha(senhaEncriptada);
            novoUsuario.setPerfil(TipoPerfil.CLIENTE);
            ((Cliente) novoUsuario).setEmpresa("Empresa Exemplo"); // ajuste conforme sua lógica
        } else if (dto.perfil() == TipoPerfil.TECNICO) {
            novoUsuario = new Tecnico();
            novoUsuario.setNome(dto.nome());
            novoUsuario.setEmail(dto.email());
            novoUsuario.setCpf(dto.cpf());
            novoUsuario.setSenha(senhaEncriptada);
            novoUsuario.setPerfil(TipoPerfil.TECNICO);
            ((Tecnico) novoUsuario).setSetor(Categoria.HARDWARE); // ajuste conforme sua lógica
        } else if (dto.perfil() == TipoPerfil.ADMIN) {
            novoUsuario = new Tecnico();
            novoUsuario.setNome(dto.nome());
            novoUsuario.setEmail(dto.email());
            novoUsuario.setCpf(dto.cpf());
            novoUsuario.setSenha(senhaEncriptada);
            novoUsuario.setPerfil(TipoPerfil.ADMIN);
            ((Tecnico) novoUsuario).setSetor(Categoria.SOFTWARE); // ou qualquer setor
        } else {
            return ResponseEntity.badRequest().body("Perfil inválido.");
        }

        usuarioRepository.save(novoUsuario);
        return ResponseEntity.ok("Usuário cadastrado com sucesso!");
    }

}
