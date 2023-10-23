package com.example.MeuAlmoxarifado.service;

import com.example.MeuAlmoxarifado.domain.model.Requisitante;

public interface RequisitanteService extends CrudService<Long, Requisitante>{
    Boolean existsById(Long id);
}
