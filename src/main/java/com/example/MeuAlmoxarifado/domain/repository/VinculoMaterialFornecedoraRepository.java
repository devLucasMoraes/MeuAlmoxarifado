package com.example.MeuAlmoxarifado.domain.repository;

import com.example.MeuAlmoxarifado.domain.model.Fornecedora;
import com.example.MeuAlmoxarifado.domain.model.VinculoMaterialFornecedora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface VinculoMaterialFornecedoraRepository extends JpaRepository<VinculoMaterialFornecedora, Long> , JpaSpecificationExecutor<VinculoMaterialFornecedora> {
    boolean existsByReferenciaFornecedoraAndFornecedora(String referenciaFornecedora, Fornecedora fornecedora);
}
