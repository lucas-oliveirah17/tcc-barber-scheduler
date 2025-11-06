package br.com.barberscheduler.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.barberscheduler.backend.model.Servico;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {
    
    Optional<Servico> findByNome(String nome);
}