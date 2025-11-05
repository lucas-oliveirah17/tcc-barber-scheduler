package br.com.barberscheduler.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import br.com.barberscheduler.backend.model.Profissional;
import br.com.barberscheduler.backend.repository.ProfissionalRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProfissionalService {
    private final ProfissionalRepository profissionalRepository;
    
    public ProfissionalService(
            ProfissionalRepository profissionalRepository, 
            UsuarioService usuarioService) {
        this.profissionalRepository = profissionalRepository;
    }
    
    // Transactional garante que as operações do banco de dados sejam atômicas, 
    // ou seja, se uma das consultas falharem, ele faz o rollback de todas as alterações
    // que fez nesta tentativa.
    @Transactional(readOnly = true)
    public List<Profissional> listarTodos() {
        return profissionalRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Profissional buscarPorId(Long id) {
        Optional<Profissional> profissional = profissionalRepository.findById(id);
        return profissional.orElseThrow(
                () -> new EntityNotFoundException(
                        "Profissional de ID " + id + " não encontrado."));
    }
    
    @Transactional
    public Profissional criar(Profissional profissional) {
        if(profissional.getUsuario() == null ||
                profissional.getUsuario().getId() == null) {
            throw new IllegalArgumentException(
                    "O ID do usuário é obrigatório para criar um profissional");
        }
        
        return profissionalRepository.save(profissional);
    }
    
    @Transactional
    public Profissional atualizar(Long id, Profissional profissionalAtualizado) {
        Profissional profissionalExistente = profissionalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Profissional de ID " + id + " não encontrado." ));
        
        profissionalExistente.setEspecialidades(profissionalAtualizado.getEspecialidades());
        profissionalExistente.setUsuario(profissionalAtualizado.getUsuario());
        
        return profissionalRepository.save(profissionalExistente);
    }
    
    public void deletar(Long id) {
        if(!profissionalRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    "Profissional de ID " + id + " não encontrado.");      
        }
        profissionalRepository.deleteById(id);
    }
}