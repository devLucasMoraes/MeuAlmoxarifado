package com.example.MeuAlmoxarifado.domain.repository;

import com.example.MeuAlmoxarifado.domain.model.Requisitante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequisitanteRepository extends JpaRepository<Requisitante, Long> {

    boolean existsByNome(String nome);
}
