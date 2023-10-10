package com.example.MeuAlmoxarifado.controller.dto.requisitante.request;

import com.example.MeuAlmoxarifado.domain.model.Requisitante;

public record EditRequisitanteDTO(
        Long id,
        String nome,
        String fone) {
    public Requisitante toModel() {
        Requisitante model = new Requisitante();
        model.setId(this.id);
        model.setNome(this.nome);
        model.setFone(this.fone);
        return model;
    }
}
