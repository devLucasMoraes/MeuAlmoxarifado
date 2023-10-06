package com.example.MeuAlmoxarifado.controller.destino.dto.response;


import com.example.MeuAlmoxarifado.domain.model.Destino;

public record ShowDestinoDTO(

        Long id,

        String nome,

        String fone

) {

    public ShowDestinoDTO(Destino destino) {
        this(destino.getId(), destino.getNome(), destino.getFone());
    }
}
