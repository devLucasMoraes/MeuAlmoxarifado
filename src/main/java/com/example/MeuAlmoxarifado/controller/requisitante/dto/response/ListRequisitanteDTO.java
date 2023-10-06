package com.example.MeuAlmoxarifado.controller.requisitante.dto.response;

import com.example.MeuAlmoxarifado.domain.model.Requisitante;

public record ListRequisitanteDTO(

        Long id,

        String nome,

        String fone
) {

    public ListRequisitanteDTO(Requisitante requisitante) {
        this(requisitante.getId(), requisitante.getNome(), requisitante.getFone());
    }
}
