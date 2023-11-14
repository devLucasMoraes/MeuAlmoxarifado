package com.example.MeuAlmoxarifado.service;

import com.example.MeuAlmoxarifado.domain.model.Transportadora;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface TransportadoraService extends CrudService<Long, Transportadora> {
    Boolean existsById(Long id);

    Transportadora getByCnpj(String cnpj);

    Page<Transportadora> dynamicFindAll(Specification<Transportadora> spec, Pageable pageable);
}
