package com.example.MeuAlmoxarifado.service;

import com.example.MeuAlmoxarifado.domain.model.LocalDeAplicacao;

public interface LocalDeAplicacaoService extends CrudService<Long, LocalDeAplicacao>{
    Boolean existsById(Long id);
}
