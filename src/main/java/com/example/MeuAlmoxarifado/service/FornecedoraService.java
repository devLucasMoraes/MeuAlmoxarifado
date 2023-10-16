package com.example.MeuAlmoxarifado.service;

import com.example.MeuAlmoxarifado.domain.model.Fornecedora;

public interface FornecedoraService extends CrudService<Long, Fornecedora>{
    Boolean existsById(Long id);
}
