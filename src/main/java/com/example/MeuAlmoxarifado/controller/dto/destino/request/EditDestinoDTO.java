package com.example.MeuAlmoxarifado.controller.dto.destino.request;

import com.example.MeuAlmoxarifado.domain.model.Destino;

public record EditDestinoDTO(
        Long id,
        String nome,
        String fone) {
    public Destino toModel() {
        Destino model = new Destino();
        model.setId(this.id);
        model.setNome(this.nome);
        model.setFone(this.fone);
        return model;
    }
}
