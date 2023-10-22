package com.example.MeuAlmoxarifado.controller.dto.response;

import com.example.MeuAlmoxarifado.domain.model.Requisitante;

public record ShowRequisitanteDTO(

        Long id,

        String nome,

        String fone
) {

    public ShowRequisitanteDTO(Requisitante requisitante) {
        this(requisitante.getId(), requisitante.getNome(), requisitante.getFone());
    }
}
