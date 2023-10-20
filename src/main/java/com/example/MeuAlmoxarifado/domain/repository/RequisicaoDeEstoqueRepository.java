package com.example.MeuAlmoxarifado.domain.repository;

import com.example.MeuAlmoxarifado.domain.model.RequisicaoDeEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequisicaoDeEstoqueRepository extends JpaRepository<RequisicaoDeEstoque, Long> {
}
