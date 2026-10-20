package com.nexaclinica.api.service;

import com.nexaclinica.api.dto.LoginResponse;
import com.nexaclinica.api.entity.Usuario;
import com.nexaclinica.api.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public LoginResponse autenticar(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "E-mail ou senha inválidos"));

        if (!usuario.getSenha().equals(senha)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "E-mail ou senha inválidos");
        }

        if (Boolean.FALSE.equals(usuario.getAtivo())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuário desativado. Contate a administração.");
        }

        return new LoginResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getRole(),
                usuario.getAtivo()
        );
    }

    public Usuario salvar(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "E-mail já cadastrado");
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizar(Long id, Usuario dados) {
        Usuario existente = buscarPorId(id);
        existente.setNome(dados.getNome());
        existente.setEmail(dados.getEmail());
        existente.setRole(dados.getRole());
        if (dados.getSenha() != null && !dados.getSenha().isBlank()) {
            existente.setSenha(dados.getSenha());
        }
        return usuarioRepository.save(existente);
    }

    public Usuario ativarDesativar(Long id) {
        Usuario existente = buscarPorId(id);
        existente.setAtivo(!existente.getAtivo());
        return usuarioRepository.save(existente);
    }

    public void deletar(Long id) {
        buscarPorId(id); // ensures 404 if not found
        usuarioRepository.deleteById(id);
    }
}
