package com.example.MeuAlmoxarifado.controller.dto.destino.response;

import com.example.MeuAlmoxarifado.domain.model.Destino;

public record ListDestinoDTO(

        Long id,

        String nome,

        String fone

) {

    public ListDestinoDTO(Destino destino) {
        this(destino.getId(), destino.getNome(), destino.getFone());
    }
}
