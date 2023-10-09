package com.example.MeuAlmoxarifado.domain.repository;

import com.example.MeuAlmoxarifado.domain.model.Fornecedora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FornecedoraRepository extends JpaRepository<Fornecedora, Long> {

    boolean existsByCnpj(String cnpj);
}
