package com.example.MeuAlmoxarifado.service;

import com.example.MeuAlmoxarifado.domain.model.Fornecedora;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface FornecedoraService extends CrudService<Long, Fornecedora>{
    Boolean existsById(Long id);

    Page<Fornecedora> dynamicFindAll(Specification<Fornecedora> spec, Pageable pageable);

    Fornecedora getByCnpj(String cnpj);
}
