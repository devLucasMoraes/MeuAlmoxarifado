package com.example.MeuAlmoxarifado.controller.requisitante.dto.request;

import com.example.MeuAlmoxarifado.domain.model.Requisitante;
import jakarta.validation.constraints.NotBlank;

public record NewRequisitanteDTO(
        @NotBlank
        String nome,
        @NotBlank
        String fone) {
    public Requisitante toModel() {
        Requisitante model = new Requisitante();
        model.setNome(this.nome);
        model.setFone(this.fone);
        return model;
    }
}
