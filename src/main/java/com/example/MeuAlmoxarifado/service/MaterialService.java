package com.example.MeuAlmoxarifado.service;

import com.example.MeuAlmoxarifado.domain.model.Material;

public interface MaterialService extends CrudService<Long, Material>{
    Boolean existsById(Long id);

}
