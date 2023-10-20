package com.example.MeuAlmoxarifado.domain.repository;


import com.example.MeuAlmoxarifado.domain.model.LocalDeAplicacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalDeAplicacaoRepository extends JpaRepository<LocalDeAplicacao, Long> {
    boolean existsByNome(String nome);
}
