package br.com.barberscheduler.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.barberscheduler.backend.model.Profissional;

@Repository
public interface ProfissionalRepository extends JpaRepository<Profissional, Long>{
    
    @Query("SELECT p FROM Profissional p JOIN FETCH p.usuario")
    List<Profissional> findAllWithUsuario();
    
    @Query("SELECT p FROM Profissional p JOIN FETCH p.usuario WHERE p.id = :id")
    Optional<Profissional> findByIdWithUsuario(@Param("id") Long id);
}