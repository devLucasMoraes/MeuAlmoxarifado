package com.example.MeuAlmoxarifado.service;

import com.example.MeuAlmoxarifado.domain.model.Transportadora;

public interface TransportadoraService extends CrudService<Long, Transportadora>{
    Boolean existsById(Long id);
}
