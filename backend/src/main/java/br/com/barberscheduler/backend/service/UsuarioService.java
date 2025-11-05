package br.com.barberscheduler.backend.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import br.com.barberscheduler.backend.model.Usuario;
import br.com.barberscheduler.backend.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    
    public UsuarioService(
            UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }
    
    public Usuario buscarPorId(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.orElseThrow(
                () -> new EntityNotFoundException(
                        "Usuario de ID " + id + " não encontrado."));
    }
    
    public Usuario criar(Usuario usuario) {
        if(usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new IllegalArgumentException(
                    "E-mail já cadastrado.");
        }
        
        // AQUI POSTERIOMENTE ENTRA A CRIPTOGRAFIA DA SENHA POR SPRING SECURITY
        
        return usuarioRepository.save(usuario); 
    }
    
    public Usuario atualizar(Long id, Usuario usuarioAtualizado) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Usuario de ID " + id + " não encontrado."));
        
        usuarioExistente.setNome(usuarioAtualizado.getNome());
        usuarioExistente.setEmail(usuarioAtualizado.getEmail());
        usuarioExistente.setTelefone(usuarioAtualizado.getTelefone());
        
        return usuarioRepository.save(usuarioExistente);
    }
    
    public void deletar(Long id) {
        if(!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    "Usuario de ID " + id + " não encontrado.");
        }
        usuarioRepository.deleteById(id);
    }
}
