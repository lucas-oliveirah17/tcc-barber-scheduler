package br.com.barberscheduler.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import br.com.barberscheduler.backend.model.Servico;
import br.com.barberscheduler.backend.service.ServicoService;

@RestController
@RequestMapping("/api/servicos")
public class ServicoController {
    @Autowired
    private ServicoService servicoService;
    
    @PostMapping
    public ResponseEntity<Servico> criarServico(@RequestBody Servico servico) {
        Servico novoServico = servicoService.criarServico(servico);
        return ResponseEntity.status(201).body(novoServico);
    }
    
    @GetMapping
    public ResponseEntity<List<Servico>> listarTodosServicos() {
        return ResponseEntity.ok(servicoService.listarTodos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Servico> buscarServicoPorId(@PathVariable Long id) {
        return servicoService.buscarPorId(id)
                .map(servico -> ResponseEntity.ok(servico))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Servico> atualizarServico(
            @PathVariable Long id, 
            @RequestBody Servico servicoAtualizado) {
        try {
            Servico servico = servicoService.atualizarServico(id, servicoAtualizado);
            return ResponseEntity.ok(servico);
        } catch(RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Servico> deletarServico(@PathVariable Long id) {
        try {
            servicoService.deletarServico(id);
            return ResponseEntity.noContent().build();
        } catch(RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}