package br.com.barberscheduler.backend.controller;

import org.springframework.http.HttpStatus;
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

import br.com.barberscheduler.backend.model.Profissional;
import br.com.barberscheduler.backend.service.ProfissionalService;

@RestController
@RequestMapping("/api/profissionais")
public class ProfissionalController {
    private final ProfissionalService profissionalService;
    
    public ProfissionalController(ProfissionalService profissionalService) {
        this.profissionalService = profissionalService;
    }
    
    @PostMapping
    public ResponseEntity<Profissional> criar(
            @RequestBody Profissional profissional) {
        Profissional novoProfissional = profissionalService.criar(profissional);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProfissional);
    }
    
    @GetMapping
    public ResponseEntity<List<Profissional>> listarTodos() {
        List<Profissional> profissionais = profissionalService.listarTodos();
        return ResponseEntity.ok(profissionais);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Profissional> buscarPorId(
            @PathVariable Long id) {
        Profissional profissionalEncontrado = profissionalService.buscarPorId(id);
        return ResponseEntity.ok(profissionalEncontrado);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Profissional> atualizar(
            @PathVariable Long id,
            @RequestBody Profissional profissional) {
        Profissional profissionalAtualizado = profissionalService.atualizar(id, profissional);
        return ResponseEntity.ok(profissionalAtualizado);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id) {
        profissionalService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
