package com.example.MeuAlmoxarifado.domain.repository;

import com.example.MeuAlmoxarifado.domain.model.VinculoMaterialComFornecedora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VinculoMaterialComFornecedoraRepository extends JpaRepository<VinculoMaterialComFornecedora, Long> {
    VinculoMaterialComFornecedora getReferenceByCodProd(String codProd);

    List<VinculoMaterialComFornecedora> findAllByCodProd(String codProd);
}
