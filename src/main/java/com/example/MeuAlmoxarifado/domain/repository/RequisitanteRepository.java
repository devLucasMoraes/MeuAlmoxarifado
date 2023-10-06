package com.example.MeuAlmoxarifado.domain.repository;

import com.example.MeuAlmoxarifado.domain.model.Requisitante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequisitanteRepository extends JpaRepository<Requisitante, Long> {
    Page<Requisitante> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
