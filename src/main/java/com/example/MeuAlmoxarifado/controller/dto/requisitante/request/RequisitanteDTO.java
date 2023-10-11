package com.example.MeuAlmoxarifado.controller.dto.requisitante.request;

import com.example.MeuAlmoxarifado.domain.model.Requisitante;
import jakarta.validation.constraints.NotBlank;

public record RequisitanteDTO(
        Long id,
        @NotBlank
        String nome,
        @NotBlank
        String fone) {
    public Requisitante toModel() {
        Requisitante model = new Requisitante();
        model.setId(this.id);
        model.setNome(this.nome);
        model.setFone(this.fone);
        return model;
    }
}
