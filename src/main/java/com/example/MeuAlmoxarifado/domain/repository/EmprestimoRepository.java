package com.example.MeuAlmoxarifado.domain.repository;

import com.example.MeuAlmoxarifado.domain.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
}
