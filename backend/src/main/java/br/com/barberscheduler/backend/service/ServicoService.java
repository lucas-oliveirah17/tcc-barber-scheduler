package br.com.barberscheduler.backend.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import br.com.barberscheduler.backend.model.Servico;
import br.com.barberscheduler.backend.repository.ServicoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ServicoService {
    private final ServicoRepository servicoRepository;
    
    public ServicoService(
            ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }
    
    public List<Servico> listarTodos() {
        return servicoRepository.findAll();
    }
    
    public Servico buscarPorId(Long id) {
        Optional<Servico> servico = servicoRepository.findById(id);
        return servico.orElseThrow(
                () -> new EntityNotFoundException(
                        "Serviço de ID " + id + " não encontrado."));
    }
    
    public Servico criar(Servico servico) { 
        if(servicoRepository.findByNome(servico.getNome()).isPresent()) {
            throw new IllegalArgumentException(
                    "Nome de serviço já cadastrado.");
        }
        
        return servicoRepository.save(servico);
    }
    
    public Servico atualizar(Long id, Servico servicoAtualizado) {
        Servico servicoExistente = servicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Serviço de ID " + id + " não encontrado."));
        
        servicoExistente.setNome(servicoAtualizado.getNome());
        servicoExistente.setDescricao(servicoAtualizado.getDescricao());
        servicoExistente.setDuracaoMinutos(servicoAtualizado.getDuracaoMinutos());
        servicoExistente.setPreco(servicoAtualizado.getPreco());
        
        return servicoRepository.save(servicoExistente);
    }
    
    public void deletar(Long id) {
        if(!servicoRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    "Serviço de ID " + id + " não encontrado.");
        }
        servicoRepository.deleteById(id);
    }
}