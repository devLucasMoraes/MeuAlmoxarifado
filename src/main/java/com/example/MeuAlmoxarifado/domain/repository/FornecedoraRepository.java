package com.example.MeuAlmoxarifado.domain.repository;

import com.example.MeuAlmoxarifado.domain.model.Fornecedora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FornecedoraRepository extends JpaRepository<Fornecedora, Long>, JpaSpecificationExecutor<Fornecedora> {

    boolean existsByCnpj(String cnpj);

    Optional<Fornecedora> getReferenceByCnpj(String cnpj);
}
